#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#define TRUE 1
void * threadFn(void *unused)
{
    
    for(;;){} //bucla infinita
    printf("FINISHED");
    return NULL;
    
    
}
int main(){
    int sum=0;
    pthread_t tid;
    while(TRUE){
        if(pthread_create(&tid,NULL,threadFn,NULL)!=0){
            
            printf("Numarul total de thread-uri este : %d",sum);
            return -1;
            
            
        }
        else{
            
            sum++;
            if(sum % 1000 == 0){ //punem sa se afiseze din 1000 in 1000 ca sa ne dam seama daca functioneaza programul
                printf("L-a momentul actual s-au format %d thread-uri\n", sum);}
        }
        
        
    }
    
    
    
    return 0;
}
