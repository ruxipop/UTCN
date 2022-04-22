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
{
    int fd=-1;
    fd=open(FIFO_INT,O_RDONLY);
    if(fd==-1){
    unlink(FIFO_INT);
    perror("Could not open FIFO");
    exit(1);
    }
    for(;;){
    
    
    
    }
    close(fd);

    return 0;
}
