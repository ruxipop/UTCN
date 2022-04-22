#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include<fcntl.h>
#include<semaphore.h>
#include<sys/stat.h>
#include<time.h>

sem_t H_sem ;
sem_t S_sem;
sem_t O_sem ;
int i = 0;
pthread_t th[1000];
	int th_id[1000];

	
void* god(void* args)
{
	while(1)
	{
		sem_wait(&H_sem);
		sem_wait(&H_sem);
		sem_wait(&S_sem);
		sem_wait(&O_sem);
		sem_wait(&O_sem);
		sem_wait(&O_sem);
		sem_wait(&O_sem);
		usleep(10000);
		printf("[YES] H2SO4 created\n");
	}
	return NULL;
}


void* atomH(void* args)
{
	int id = *(int*) args;
	printf("[H] created\n");
	sem_post(&H_sem);
	return NULL;
}

void* atomS(void* args)
{
	int id = *(int*) args;
	printf("[S] created\n");
	sem_post(&S_sem);
	return NULL;
}

void* atomO(void* args)
{
	int id = *(int*) args;
	printf("[O] created\n");
	sem_post(&O_sem);
	return NULL;
}


void* create_H(void * args)
{
	while(1)
	{
		if(rand() % 10000000 == 1)
		{
			th_id[i] = i;
			if(pthread_create(&th[i],NULL,atomH,(void*)&th_id[i]) != 0)
			{
				perror("Cannot create atomNa thread");
				exit(1);
			}
			i++;
		}
	}
}

void* create_S(void * args)
{
	while(1)
	{
		if(rand() % 10000000 == 1)
		{
			th_id[i] = i;
			if(pthread_create(&th[i],NULL,atomS,(void*)&th_id[i]) != 0)
			{
				perror("Cannot create atomNa thread");
				exit(1);
			}
			i++;
		}
	}
}

void* create_O(void * args)
{
	while(1)
	{
		if(rand() % 10000000 == 1)
		{
		th_id[i] = i;
			if(pthread_create(&th[i],NULL,atomO,(void*)&th_id[i]) != 0)
			{
				perror("Cannot create atomNa thread");
				exit(1);
			}
			i++;
		}
	}
}

int main()
{
	srand(time(NULL));

	
	 sem_init(&H_sem,0,0);
	
	
	sem_init(&S_sem,0,0);
	
	
	sem_init(&O_sem,0,0);
		

	
	if(pthread_create(&th[0],NULL,god,NULL) != 0)
	{
		perror("Cannot create god thread");
		exit(1);
	}

	
	i++;

	
	th_id[i] = i;
	
	


{
		if(pthread_create(&th[i],NULL,create_H,(void*)&th_id[i]) != 0)
		{
			perror("Cannot create atomNa thread");
			exit(1);

		}
	
}
{
	
	
		if(pthread_create(&th[i],NULL,create_S,(void*)&th_id[i]) != 0)
		{
			perror("Cannot create atomCl thread");
			exit(1);

		}
	}

	{

	
	
		if(pthread_create(&th[i],NULL,create_O,(void*)&th_id[i]) != 0)
		{
			perror("Cannot create atomCl thread");
			exit(1);
		}
		}
	
	
	      scanf("%*c");
	for(int i = 0 ; i <= 3 ; i++)
	{   pthread_cancel(th[i]);
		pthread_join(th[i],NULL);
	}
	sleep(1);

	

	

	
	sem_destroy(&H_sem);
	sem_destroy(&S_sem);
	sem_destroy(&O_sem);
	return 0;
}


