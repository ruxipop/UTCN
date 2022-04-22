
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#define NR_THREADS 9

void* thread_FN(void *param) {
    long *i=(long*)param;
    while(1) {
        sleep(rand() % 5 + 1);
        printf("%ld  ", *i);
        fflush(stdout);
    }
    return NULL;
}
int main(int argc, char **args) {
    pthread_t tid[NR_THREADS];
    int a;
    long index[NR_THREADS];
    
    for(long i = 0; i < NR_THREADS; ++i){
        index[i]=i+1;
        if(pthread_create(tid + i, NULL, thread_FN, &(index[i])) != 0) {
            perror("Nu s-a putut creea thread-ul \n");
            return -1;
        }
    }
    for(long i = NR_THREADS; i > 0;) {
        printf("Introduceti nr thread-ului pe care doriti sa-l anulati \n");
        
        if(scanf("%d", &a)==1 && tid[a - 1] != 0 && a >= 1 && a <=9 ) {
            i--;
            pthread_cancel(tid[a - 1]);
            
            printf("\nThread %d finished\n", a);
            tid[a - 1] = 0;
        } else {
            printf("Invalid thread number\n");
        }
    }
    
    for(long i=0;i<NR_THREADS;i++)
    pthread_join(tid[i], NULL);
    printf("S-a incheiat simularea\n");
    return 0;
}




