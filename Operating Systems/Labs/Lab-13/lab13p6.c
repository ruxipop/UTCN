#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
typedef  struct{
    
    int start;
    int nrThreaduri;
    int n;
}TH_THREAD;


int isPerfect(int nr){
    int sum=0;
    for(int i=1;i<=nr/2;i++){
        
        if(nr %i==0){
            
            
            sum+=i;
        }
        
    }
    return sum==nr;
}

void * verificare_number(void *aux){
    
    TH_THREAD *arg=(TH_THREAD*)aux;
    for(int i=arg->start;i<arg->n;i+=arg->nrThreaduri){
        if(isPerfect(i)){
            printf("nr perfect : %d\n",i);
            
        }
        
        
    }
    
}
int main(int argc,char ** argv)

{
    
    int m,n;
    pthread_t *tid=NULL;
    TH_THREAD *threadA=NULL;
    
    if(argc!=3){
        
        printf("error");
        return -1;
        
    }
    
    n=atoi(argv[2]);
    m=atoi(argv[1]);
    printf("M=%d,N= %d \n",m,n);
    tid=(pthread_t*)malloc(m*sizeof(pthread_t));
    if(tid==NULL){
        printf("faild alloc");
        return -2;
        
        
    }
    threadA=(TH_THREAD*)malloc(m*sizeof(TH_THREAD));
    
    if(threadA==NULL){
        printf("faild alloc");
        free(tid);
        return -3;
        
        
    }
    for(int i=0;i<m;i++){
        threadA[i].n=n;
        threadA[i].nrThreaduri=m;
        threadA[i].start=i+1;
        pthread_create(&tid[i],NULL,verificare_number,(void*)&threadA[i]);
    }
    
    
    for(int i=0;i<m;i++){
        pthread_join(tid[i],NULL);
    }
    
    
    return 0;
    
    
    
}
