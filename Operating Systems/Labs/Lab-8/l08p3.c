#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>

//nu am reusit sa o fac complet
typedef struct {
    int id;
    sem_t semF;
} thread_ab;


void *thread_fn(void *params){
    thread_ab*s = (thread_ab*) params;
    
    
    
    for(;;){
        
        
        sem_wait(&s->semF);
        
        printf( "%d  ", s->id);
        fflush(stdout);
        usleep(rand() % 100);
        sem_post(&s->semF);
        
        
    }
    
    
    
    return NULL;
}

int main(int args, char**argv){
    
    int n = atoi(argv[1]);
    thread_ab params[n];
    sem_t  sem;
    
    if(sem_init(&sem, 0, n) != 0){
        printf("Could not open semaphore!\n");
        return -1;
        
    }
    
    
    srand(time(NULL));
    pthread_t threads[n];
    
    for(int i = n; i >0; i--){
        
        params[i].id = i;
        params[i].semF = sem;
        pthread_create(&threads[i], NULL, thread_fn, &params[i]);
        sem_post(&sem);
    }
    for(int i = n-1; i >0; i--){
        pthread_join(threads[i], NULL);
    }
    
    
    
    sem_destroy(&sem);
    return 0;
}
