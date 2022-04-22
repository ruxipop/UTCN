#include <stdio.h>
#include <semaphore.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
int n=5;
int  v[100];
int nrDiv[100];

int calc_Div(int x){
int nr=0;
if(x==0)
return 0;
for(int i=1;i<=x;i++){

         if(x%i==0){
         
         nr++;}}
         return nr;


}
int posN=0;
 void *thread_function(void * args){
 pthread_mutex_t *lock=(pthread_mutex_t*)args;
  int pos;
while(1){
pthread_mutex_lock(lock);
pos=posN;
posN++;
pthread_mutex_unlock(lock);


if(pos>=n) break;
 nrDiv[pos]=calc_Div(v[pos]);

 }

 return NULL;
 }
 
 
int main(int argc, char *argv[]){
 int i;
 int nrT;

 
 if(sscanf(argv[1],"%d",&nrT)!=1){
 perror("Nu se poate citi nr de thread-uri");
 return -1;
 
 }
 pthread_t tids[nrT];
 pthread_mutex_t lock;
 for(int i=0;i<n;i++){
 v[i]=random()%8;
 
 
 }
 
 if(pthread_mutex_init(&lock,NULL)!=0){
 perror("error initializing the mutex");


 return -1;
 }

for(int i=0;i<nrT;i++){
pthread_create(&tids[i],NULL,thread_function,&lock);

}
for(int i=0;i<nrT;i++){
pthread_join(tids[i],NULL);

}

pthread_mutex_destroy(&lock);

for(i=0;i<n;i++)
    printf("%d ",v[i]);
printf("\n");
for(i=0;i<n;i++)
    printf("%d ",nrDiv[i]);


return 0;}
