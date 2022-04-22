

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdint.h>
#include <sys/mman.h>
#include <fcntl.h>
int main(int argc,char **argv){
    int fd1,fd2,size1,size2;
    char *addr1,*addr2;
    char  aux;
    
    
    if(argc!=3){
        
        printf("%s <f1> <f2> \n",argv[0]);
        return -1;
    }
    
    if((fd1=open(argv[1],O_RDWR))==-1){
        perror("rror");
        return -2;
        
        
    }
    if((fd2=open(argv[2],O_RDWR))==-1){
        perror("rror");
        close(fd1);
        return -3;
        
        
    }
    
    size1=lseek(fd1,0,SEEK_END);
    lseek(fd1,0,SEEK_SET);
    
    
    
    size2=lseek(fd2,0,SEEK_END);
    lseek(fd2,0,SEEK_SET);
    addr1=mmap(NULL,size1,PROT_WRITE|PROT_READ,MAP_SHARED,fd1,0);
    if(addr1==NULL){
        perror("fail map1");
        close(fd1);
        close(fd2);
        return -4;
        
        
    }
    
    
    
    addr2=mmap(NULL,size2,PROT_WRITE|PROT_READ,MAP_SHARED,fd2,0);
    if(addr2==NULL){
        perror("fail map1");
        munmap(addr1,size1);
        close(fd1);
        close(fd2);
        return -4;
        
        
    }
    for(int i=0;i<size1;i++){
        
        aux=addr1[i];
        addr1[i]=addr2[i];
        addr2[i]=aux;
        
        
        
        
    }
    
    
    
    munmap(addr1,size1);
    munmap(addr2,size2);
    close(fd1);
    close(fd2);
    
    
    
    
}
