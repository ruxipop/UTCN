#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>
#include <sys/wait.h>
#include <stdint.h>




#define VOCALE "aeiouAEIOU"
#define MAX_THREADS 100


int vowelCount=0;
pthread_mutex_t  mut=PTHREAD_MUTEX_INITIALIZER;
void *nr_vocale( void *aux)


{
    
    char *car=(char*)aux;
    for(int i=0;i<strlen(car);i++){
        for(int j=0; j<10   ;j++){
            if(car[i]==VOCALE[j]){
                
                pthread_mutex_lock(&mut);
                vowelCount++;
                
                pthread_mutex_unlock(&mut);
                
                break;
                
                
                
                
            }
            
            
            
            
        }
        
    }
    return NULL;
    
}


int main(int argc,char **argv){
    pthread_t tid[MAX_THREADS];
    if(argc<2){
        
        printf("introdu cuvinte");
        return -1;
        
    }
    for(int i=1;i<argc;i++){
        pthread_create(&tid[i-1],NULL,nr_vocale,(void*)argv[i]);
        
    }
    
    for(int i=1;i<argc;i++){
        pthread_join(tid[i-1],NULL);
        
    }
    printf("nr vocale : %d \n", vowelCount);
    pthread_mutex_destroy(&mut);
    return 0;
}
