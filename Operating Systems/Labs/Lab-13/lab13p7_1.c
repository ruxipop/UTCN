#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>

#define FIFO_NAME "fifo_ex7_lab13"

int listFolder(char *path, int fd){
    DIR *dir;
    struct dirent *dirEntry;
    struct stat statbuf;
    char fullPath[512], c;
    if((dir = opendir(path)) == NULL){
        perror("failed opendir");
        return -1;
    }
    while((dirEntry = readdir(dir)) != NULL){
        if(strcmp(dirEntry->d_name, ".") != 0 && strcmp(dirEntry->d_name, "..") != 0){
            snprintf(fullPath, 512, "%s/%s", path, dirEntry->d_name);
            if(lstat(fullPath, &statbuf) == -1){
                perror("lstat fail");
                return -2;
            }
            // lstat ok
            if(S_ISREG(statbuf.st_mode)){
                if(write(fd, dirEntry->d_name, strlen(dirEntry->d_name)) != strlen(dirEntry->d_name)){
                    printf("write fail for %s\n", dirEntry->d_name);
                    return -3;
                }
                c = '\n';
                if(write(fd, &c, 1) != 1){
                    printf("fail write newline\n");
                    return -4;
                }
            }
            
        }
        
    }
    closedir(dir);
    return 0;
    
}

int main(int argc, char **argv){
    
    int fd; 
    char c;
    if(mkfifo(FIFO_NAME, 0600) == -1){
        perror("failed mkfifo");
        return -2;
    }
    
    fd = open(FIFO_NAME, O_WRONLY);
    if(fd == -1){
        perror("open failed");
        unlink(FIFO_NAME);
        return -3;
    }
    
    listFolder(argv[1], fd);
    
    c = 0;
    write(fd, &c, 1); // verificat succes
    
    
    close(fd);
    unlink(FIFO_NAME);
    
    return 0;
    
}
