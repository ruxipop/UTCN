



#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>

#include <time.h>

// e facut dupa ex din lab, nu am echilibrat munca thread-urilor
typedef struct {
    int *arr;
    int from;
    int to;
} TH_STRUCT;

void generate_n_array(int *arr, int size)
{
    for(int i = 0; i < size; i++)
    arr[i] = i + 1;
}

void *thread_fn_count(void *param)
{
    TH_STRUCT* s = (TH_STRUCT*) param;
    int count=0;
    
    for(int i = s->from; i <= s->to; i++) {
        while(s->arr[i] > 0){
            if(s->arr[i] % 10 == 1)
                count++;
            s->arr[i] = s->arr[i] / 10;
        }
    }
    return (void*)(long)count;
}

int main(int argc, char** argv)


{  clock_t start, end;
    int i;
    double timeP;
    void *result;
    int thMax, max = 0;
    
    int nr_threads;
    int nr;
    int *v = (int*)malloc(atoi(argv[1]) * sizeof(int));
    
    
    if(argc != 3) {
        perror("Numar gresit de argumente");
        return -1;
    }
    if(sscanf(argv[1], "%d", &nr) != 1) {
        perror("Numarul este invalid");
        return -1;
    }
    
    if(sscanf(argv[2], "%d", &nr_threads) != 1) {
        perror("Numarul de thread-uri este invalid");
        return -1;
    }
    
    pthread_t tid[nr_threads];
    TH_STRUCT params[nr_threads];
    srand(time(NULL));
    generate_n_array(v, atoi(argv[1]));
    
    for(i = 0; i < nr_threads; i++){
        params[i].arr = v;
        if(i == 0){
            params[i].from = 0;
        }else{
            params[i].from = params[i-1].to + 1;
        }
        params[i].to = params[i].from + atoi(argv[1]) / nr_threads - 1;
        
        
        if(i < atoi(argv[1]) % nr_threads){
            params[i].to++;
        }
        
        pthread_create(&tid[i], NULL, thread_fn_count, &params[i]);
    }
    for(i = 0; i <nr_threads; i++){
        start=clock();
        pthread_join(tid[i], &result);
        end=clock();
        thMax = (int)(long)result;
        max += thMax;
        timeP= (double)(end - start) / CLOCKS_PER_SEC;
        printf(" thread  %d spend: %lf\n", i, timeP);
    }
    printf("Number of ones is %d.\n", max);
    
    
    
    return 0;
}


