
// rezultatul ar trebui sa fie 4000 deoarece sunt 4 thread-uri care efectueaza for-ul de 1000 ori
//intitial o sa dea doar M adica 1000
//pentru a sincroniza programul ,am introdus for-ul in zona critica

#include <stdio.h>
#include <semaphore.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#define N 4
#define M 1000

long long count=0;

void *thread_function(void *unused){
    int i;
    long aux;
    sem_t *semF=(sem_t*)unused;
    sem_wait(semF);//regiune critica
    for(i=0;i<M;i++){ 
        
        aux=count;
        aux++;
        usleep(random()%10);
        count=aux;
        
        
        
        
        
        
    }
    sem_post(semF);
    return NULL;
    
    
    
}


int main(){
    pthread_t threads[N];
    sem_t semF;
    if(sem_init(&semF,0,1)!=0){  // se initializeaza semaforul cu o permisiune
        
        printf("nu s-a putut deschide semaforul");
        return -1;
    }
    for(int i=0;i<N ;i++){
        
        pthread_create(&threads[i],NULL,thread_function,&semF); //la fiecare thread ii spunem ca avem semafor 
        
        
        
        
    }
    
    
    for(int i=0;i<N ;i++){
        
        pthread_join(threads[i],NULL);
        
        
        
        
    }
    sem_destroy(&semF);
    printf("count =%lld\n",count);
    return 0;
    
    
}
