#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <pthread.h>
#include "a2_helper.h"
#include <semaphore.h>

typedef struct _PARAM
{
	int pid,tid;
}PARAM;
pthread_mutex_t lock_p2_ended;
int p2_threads_ended=0;
int t22_started=0,t24_terminated=0;

pthread_mutex_t lock_p3_ended;
int p3_threads_ended=0;
sem_t p3_sem;
pthread_mutex_t t11_others_lock;
int t11_ended=0,t11_started=0,t11_others_count=0;

pthread_mutex_t lock_p7_ended;
int p7_threads_ended=0;

void*f(void*param)
{
	int pid=((PARAM*)param)->pid;
	int tid=((PARAM*)param)->tid;
	if(pid==2)
	{
		if(tid==4)
		{
			while(0==t22_started)
			{

			}
		}
		if(tid==1)
		{
			sem_t*sem=sem_open("t75_terminated",O_EXCL);
			sem_wait(sem);
			sem_close(sem);
		}
		info(BEGIN,pid,tid);
		if(tid==2)
		{
			t22_started=1;
			while(0==t24_terminated)
			{

			}
		}
		info(END,pid,tid);
		if(tid==1)
		{
			sem_t*sem=sem_open("t21_terminated",O_EXCL);
			sem_post(sem);
			sem_close(sem);
		}
		if(tid==4)
		{
			t24_terminated=1;
		}
		pthread_mutex_lock(&lock_p2_ended);
		p2_threads_ended++;
		pthread_mutex_unlock(&lock_p2_ended);
		return 0;
	}
	if(pid==3)
	{
		if(tid!=11)
		{
			while(0==t11_started)
			{

			}
		}
		sem_wait(&p3_sem);
		info(BEGIN,pid,tid);
		if(tid==11)
		{
			t11_started=1;
		}
		if(tid!=11)
		{
			pthread_mutex_lock(&t11_others_lock);
			t11_others_count++;
			pthread_mutex_unlock(&t11_others_lock);
			while(0==t11_ended)
			{

			}
		}
		if(tid==11)
		{
			while(5!=t11_others_count)
			{

			}
		}
		info(END,pid,tid);
		if(tid==11)
		{
			t11_ended=1;
		}
		sem_post(&p3_sem);

		pthread_mutex_lock(&lock_p3_ended);
		p3_threads_ended++;
		pthread_mutex_unlock(&lock_p3_ended);
		return 0;
	}
	if(pid==7)
	{
		if(6==tid)
		{
			sem_t*sem=sem_open("t21_terminated",O_EXCL);
			sem_wait(sem);
			sem_close(sem);
		}
		info(BEGIN,pid,tid);
		info(END,pid,tid);
		if(5==tid)
		{
			sem_t*sem=sem_open("t75_terminated",O_EXCL);
			sem_post(sem);
			sem_close(sem);
		}
		pthread_mutex_lock(&lock_p7_ended);
		p7_threads_ended++;
		pthread_mutex_unlock(&lock_p7_ended);
	}
	return 0;
}

int main()
{
	init();
	info(BEGIN,1,0);
	pthread_t thread;
	sem_unlink("t75_terminated");
	sem_unlink("t21_terminated");
	sem_t*sem;
	sem=sem_open("t75_terminated",O_CREAT,0644,0);
	sem_close(sem);
	sem=sem_open("t21_terminated",O_CREAT,0644,0);
	sem_close(sem);
	if(0==fork())
	{
		info(BEGIN,2,0);
		PARAM p2p[5];

		pthread_mutex_init(&lock_p2_ended,0);
		for(int i=0;i<5;i++)
		{
			p2p[i].pid=2;
			p2p[i].tid=i+1;
			pthread_create(&thread,0,f,p2p+i);
		}
		while(5!=p2_threads_ended)
		{

		}
		if(0==fork())
		{
			info(BEGIN,5,0);
			if(0==fork())
			{
				info(BEGIN,6,0);
				info(END,6,0);
				return 0;
			}
			wait(NULL);
			info(END,5,0);
			return 0;
		}
		wait(NULL);
		info(END,2,0);
		return 0;
	}
	if(0==fork())
	{
		PARAM p3p[48];
		info(BEGIN,3,0);
		sem_init(&p3_sem,0,6);
		pthread_mutex_init(&t11_others_lock,0);
		pthread_mutex_init(&lock_p3_ended,0);
		for(int i=0;i<48;i++)
		{
			p3p[i].pid=3;
			p3p[i].tid=i+1;
			pthread_create(&thread,0,f,p3p+i);
		}
		while(48!=p3_threads_ended)
		{

		}
		info(END,3,0);
		return 0;
	}
	if(0==fork())
	{
		info(BEGIN,4,0);
		if(0==fork())
		{
			info(BEGIN,7,0);
			PARAM p7p[6];
			pthread_mutex_init(&lock_p7_ended,0);
			for(int i=0;i<6;i++)
			{
				p7p[i].pid=7;
				p7p[i].tid=i+1;
				pthread_create(&thread,0,f,p7p+i);
			}
			while(6!=p7_threads_ended)
			{

			}
			info(END,7,0);
			return 0;
		}
		wait(NULL);
		info(END,4,0);
		return 0;
	}
	for(int i=0;i<3;i++)
	{
		wait(NULL);
	}
	info(END,1,0);
	return 0;
}
