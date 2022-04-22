#include <stdio.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <semaphore.h>
sem_t *lock,*lock1;

int main(void) {
    int shmFd;
    int *sharedValue = NULL;
lock=sem_open("/sem",O_CREAT,0600,1);
sem_unlink("/sem");
lock1=sem_open("/sem",O_CREAT,0600,1);
sem_unlink("/sem");

    shmFd = shm_open("/l12_myshm", O_CREAT | O_RDWR, 0600);
    if(shmFd < 0) {
        perror("Could not aquire shm");
        return 1;
    }
    ftruncate(shmFd, sizeof(int));
    sharedValue = (int*)mmap(0, sizeof(int), PROT_READ | PROT_WRITE, 
        MAP_SHARED, shmFd, 0); ;
    if(sharedValue == (void*)-1){
        perror("Could not map the shared memory");
        return 1;
    }
 
    if((fork())==0){
            int shmFd;
    int *sharedValue = NULL;

    shmFd = shm_open("/l12_myshm", O_RDWR, 0);
    if(shmFd < 0) {
        perror("Could not aquire shm");
        return 1;
    }
    sharedValue = (int*)mmap(0, sizeof(int), PROT_READ | PROT_WRITE, 
        MAP_SHARED, shmFd, 0); ;
    if(sharedValue== (void*)-1){
        perror("Could not map the shared memory");
        return 1;
    }

    while(1){
       
      sem_wait(lock1);
            printf("P[2]: %d\n", *sharedValue);
            (*sharedValue)++;
            sleep(1);
            sem_post(lock);
        


    }
    munmap(sharedValue, sizeof(int));
    sharedValue = NULL;

    }else {
        


    *sharedValue = 0;
    while(1){
       
     sem_wait(lock);
            printf("P[1]: %d\n", *sharedValue);
            (*sharedValue)++;
            sleep(1);
           sem_post(lock1);
    }
    }
    wait(NULL);
    munmap(sharedValue, sizeof(int));
    sharedValue = NULL;
    shm_unlink("/l12_myshm");

    return 0;
}
