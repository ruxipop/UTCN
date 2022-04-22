#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char **argv){
    
    int fd, size;
    char *addr = NULL, *temp = NULL;
    if(argc != 4){
        printf("%s <filename> <s1> <s2>\n", argv[0]);
        return -1;
    }
    
    fd = open(argv[1], O_RDWR);
    if(fd == -1){
        perror("open failed");
        return -1;
    }
    
    size = lseek(fd, 0, SEEK_END);
    lseek(fd, 0, SEEK_SET);
    
    addr = (char *)mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if(addr == NULL){
        perror("mmap failed");
        close(fd);
        return -3;
    }
    
    temp = addr;
    while((temp = strstr(temp, argv[2])) != NULL){
        for(int i = 0; i < strlen(argv[3]); i++){
            temp[i] = argv[3][i];
        }
        temp += strlen(argv[3]);
    }
    
    munmap(addr, size);
    close(fd);
    
    return 0;
    
}
