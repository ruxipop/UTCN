#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <stdlib.h>
int main(void)
{
    int fd[2];
char *prop=(char*)malloc(30*sizeof(char));

    if(pipe(fd) != 0) {
        perror("Could not create pipe");
        return 1;
    }

    if(fork() != 0) {
        //parent
        close(fd[0]); //no use for read end
 strcpy(prop,"Buna ziua");
        write(fd[1], prop, strlen(prop));
        printf("Parent: wrote %s to pipe\n", prop);
        close(fd[1]);
        wait(NULL);
    } else {
        //child
        close(fd[1]); //no use for write end
        int i=0;
       while(1){
        
        
        read(fd[0], prop+i, sizeof(char));
        if(prop[i]==0)
        break;       
        i++; 
        }
        
        printf("Child: read %s from pipe\n", prop);
        close(fd[0]);
    }
    free(prop);

    return 0;
}


