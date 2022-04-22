#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

#define FIFO_INT "fifo"


int main(void)
{int nr=0;
    int fd=-1;
       char c = 'c';
    if(access(FIFO_INT,0)==0){
    printf("Delete pipe");
    unlink(FIFO_INT);
    }
   if(mkfifo(FIFO_INT,0600)!=0){
   
   perror("Could not open FIFO");
   exit(1);
   }
   
     fd=open(FIFO_INT,O_WRONLY);
    if(fd==-1){
    unlink(FIFO_INT);
    perror("Could not open FIFO");
    exit(1);
    }
    for(;;){
     write(fd, &c, sizeof(c));
            printf("%d\n", ++nr);
    
    
    }
    close(fd);
unlink(FIFO_INT);
    return 0;
}
