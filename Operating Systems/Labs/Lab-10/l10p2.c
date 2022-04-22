#include <stdio.h>

#include <stdlib.h>

#include <time.h>

#include <unistd.h>

#include <semaphore.h>

#include <pthread.h>


#define NR_CUSTOMERS 30
#define MAX 5
#define TRUE 1
#define FALSE 0
//ideea este ca la problema din laborator
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond_masa = PTHREAD_COND_INITIALIZER;




int waitingCustomers = 0;

int isGroup = FALSE;


void *th_func(void *args)

{

        int id = *(int*)args;


        usleep(1000 * (random() % 20));

        printf("Customer with id=%d.Entering the restaurant.\n", id);


        pthread_mutex_lock(&lock);


while(1){

if(waitingCustomers == MAX || ( waitingCustomers > 0 && isGroup==TRUE)) {

                printf("Customer with id=%d. Waiting...\n", id);

                pthread_cond_wait(&cond_masa, &lock);}
                else break;


}
      

         ++waitingCustomers;

        if( waitingCustomers == MAX)

                isGroup = TRUE;

        printf("Customer with id=%d.Serving....\n", id);

        pthread_mutex_unlock(&lock);

        usleep(1000 );

        pthread_mutex_lock(&lock);

        printf("Customer with id=%d. Leaving..\n", id);

         --waitingCustomers;

        if( waitingCustomers == 0 && isGroup == TRUE) {

                printf("A group left the restaurant.\n");

                isGroup = FALSE;

        }

        pthread_cond_signal(&cond_masa);

        pthread_mutex_unlock(&lock);

        return NULL;

}


int main()

{

     
int tidID[NR_CUSTOMERS];
        pthread_t tid[NR_CUSTOMERS];


        srandom(time(NULL));


        for(int i=0; i< NR_CUSTOMERS; i++) {
tidID[i]=i+1;
                pthread_create(&tid[i], NULL, th_func, &tidID[i]);

        }

        for(int i=0; i< NR_CUSTOMERS; i++) {

                pthread_join(tid[i], NULL);

        }

 pthread_mutex_destroy(&lock);
     pthread_cond_destroy(&cond_masa);


       
       

        return 0;

}
