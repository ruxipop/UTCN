#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>

#define FIFO_NAME "fifo_ex7_lab13"


int main(int argc, char **argv){
    
    int fd, c, pos = 0;
    char filename[512];
    
    fd = open(FIFO_NAME, O_RDONLY);
    if(fd == -1){
        perror("open failed");
        unlink(FIFO_NAME);
        return -3;
    }
    
    for(;;){
        read(fd, &c, 1);
        if(c == 0){
            break;
        }
        if(c == '\n'){
            filename[pos] = 0;
            if(filename[0] >= '0' && filename[0] <= '9'){
                printf("filename: %s\n", filename);
            }
            pos = 0;
        }
        else{
            filename[pos++] = c;
        }
    }
    
    close(fd);
    
    
    return 0;
    
}
