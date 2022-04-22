#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>



//inpirata din video de pe moodle
#define Max 2000
int number;
int v[Max];
sem_t semPrint;

sem_t sem_na;
sem_t sem_cl;
sem_t sem_na_cl;//na care sta dupa cl
sem_t sem_cl_na; //cl sta dupa na
void *cl_func(void *unused){
    sem_wait(&sem_cl_na);
    sem_post(&sem_cl);
    sem_wait(&sem_na);
    sem_wait(&semPrint);
    printf(">>>>>NaCl din t CL\n");
    sem_post(&semPrint);
    sem_post(&sem_cl_na);
    return NULL;
    
    
}

void *na_func(void *unused){
    sem_wait(&sem_na_cl);
    sem_post(&sem_na);
    sem_wait(&sem_cl);
    sem_wait(&semPrint);
    printf(">>>>>NaCl din t Na\n");
    sem_post(&semPrint);
    sem_post(&sem_na_cl);
    return NULL;
    
    
}



int main(){
    sem_init(&sem_na ,0,0);
    sem_init(&sem_cl ,0,0);
    sem_init(&sem_na_cl,0,1);
    sem_init(&sem_cl_na,0,1);
    sem_init(&semPrint,0,1);
    srand(time(NULL));
    pthread_t thread;
    while(1){
        int nb;
        printf(" 1 for Cl ,0 for Na: ");
        printf(" \n");
        scanf("%d",&nb);
        number++;
        v[number]=number;
        if(nb==1){
            printf("S-a creat Clor\n");
            pthread_create(&thread,NULL,cl_func,&v[number]);
        }
        else{
            if(nb==0){
                printf("S-a creat Natriu\n");
                pthread_create(&thread,NULL,na_func,&v[number]);}
            
            else{
                printf("ati introdus alt nr inafara de 0 si 1\n");
            }
            
        }
        if(number>2000){
            break;
        }
        
    }
    sem_destroy(&sem_na);
    sem_destroy(&sem_cl);
    sem_destroy(&sem_na_cl);
    sem_destroy(&sem_cl_na);
    sem_destroy(&semPrint);
    
    return 0;
}
