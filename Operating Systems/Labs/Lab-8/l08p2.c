#include <stdio.h>
#include <semaphore.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>


//si aici am ascultat video-ul de pe moodle 
int nrThreads=0;

void *limited_area(void *unused){
    sem_t *semF=(sem_t*) unused;
    
    
    sem_t semPrintf; //un semafor pentru printf
    
    if(sem_init(&semPrintf,0,1)!=0){  
        
        printf("nu s-a putut deschide semaforul");
        exit(1);
    }
    sem_wait(semF);
    
    nrThreads++;
    
    usleep(100);
    sem_wait(&semPrintf);
    printf("The number of threads in the limited area is :%d\n",nrThreads);
    
    sem_post(&semPrintf);
    sem_destroy(&semPrintf);
    nrThreads--;
    sem_post(semF);
    
    return NULL;
    
    
}


int main(int argc, char *argv[]){
    if(argc!=2){
        printf("numarul de argumente invalid\n");
        
        return -1;
    }
    int n=atoi(argv[1]);
    
    
    
    pthread_t threads[16];
    sem_t semF;
    if(sem_init(&semF,0,n)!=0){  
        
        printf("nu s-a putut deschide semaforul");
        return -1;
    }
    for(int i=0;i<16 ;i++){
        
        pthread_create(&threads[i],NULL,limited_area,&semF); //la fiecare thread ii spunem ca avem semafor 
        
        
        
        
    }
    
    
    for(int i=0;i<16 ;i++){
        
        pthread_join(threads[i],NULL);
        
        
        
        
    }
    sem_destroy(&semF);
    
    return 0;
    
    
}
