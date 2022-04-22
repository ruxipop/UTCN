#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdint.h>

int main()
{
    pid_t pid2 = 0, pid3 = 0, pid4 = 0;
    int status = 0;
    printf("P[1]\n");
    pid2 = fork();
    switch(pid2){
            
        case -1:  perror("Can not create process");
            exit(1);
            break;
        case 0:
            printf("P[2]\n");
            
            pid4 = fork();
            
            switch(pid4)
                
            {
                    
                case -1:  perror("Can not create process");
                    exit(1);
                    break;
                case 0:
                    
                    printf("P[4]\n");
                    exit(0);
                    
                    
                default:
                    
                    waitpid(pid4, & status, 0);
                    pid3 = fork();
                    
                    
                    switch(pid3){
                        case -1:  perror("Can not create process");
                            exit(1);
                            break;
                        case 0:
                            
                            printf("P[3]\n");
                            
                            exit(0);
                        default:
                            
                            waitpid(pid3, & status, 0);
                            
                            
                    }
                    
            }
        default:  waitpid(pid2, & status, 0);
            
    }
    return 0;
}
