#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>




int main(void)
{
    int fd[2];
   int nr = 0;
   char c = 'c';
    if(pipe(fd) != 0) {
        perror("Could not create pipe");
        return 1;
    }

    if(fork() != 0) {
  
        close(fd[0]); 
     
       for(;;){
            write(fd[1], &c, sizeof(c));
            printf("%d\n", ++nr);
        }
        close(fd[1]);
        
    } else {
        
        close(fd[1]); 

        for(;;) {
          
        }
        close(fd[0]);
    }

    return 0;
}
