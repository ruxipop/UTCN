#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>



int main()
{
    pid_t pid1=-1, pid2=-1, pid3=-1, pid4=-1;
    pid1 = fork();
    
    if (pid1 == -1) {
        perror("P1 could not create P2");
        return -1;
    }
    
    if (pid1 == 0) {
        pid3 = fork();
        if (pid3 == -1) {
            perror("P2 could not create P4");
            return -1;
        } else if (pid3 == 0) {
            printf("P4 my pid is  %d and P2 parent pid is %d\n", getpid(), getppid());
            sleep(60);
        } else {
            printf("P2 my pid is %d, P1 parent pid is %d, P4 child pid is %d \n", getpid(), getppid(), pid3);
            wait(NULL);
        }
    } else {
        pid2 = fork();
        if(pid2 == -1) {
            perror("P1 could not create P3");
            return -1;
        } else if (pid2 == 0) {
            pid4 = fork();
            if (pid4 == -1) {
                perror("P3 could not create P5");
                return -1;
            } else if (pid4 == 0) {
                printf("P5  my pid is %d and P3 parent pid is %d \n", getpid(), getppid());
                sleep(60);
            } else {
                printf("P3 my pid is %d and P1 parent pid is %d  P5 child pid is %d\n", getpid(), getppid(), pid4);
                wait(NULL);
            }
        } 
        else{
            
            printf("P1 my pid is  %d and children are P2 with pid=  %d &  P3 with pid = %d \n", getpid(), pid1, pid2);
            wait(NULL);
            wait(NULL);
        }
    }
    return 0;
}
