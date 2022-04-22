#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>



void delete(char * data,off_t size,int fd){


char* newD = (char*)mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
off_t newS=0;
char vocaleM[5] = {'a', 'e', 'i', 'o', 'u'};
char vocaleMa[5]={'A', 'E', 'I', 'O', 'U'};
int nrV=0,i=0,j=0;
while( i < size) {
int ok=0;
       for(j=0;j<5;j++){
       
            if(data[i] == vocaleM[j] || data[i]==vocaleMa[j] ) {
            ok=1;
               nrV++;
            }
            
            }
            if(ok==0){
            
            
            
            
            newD[newS]=data[i];
               newS++;
            }
            
        
        i++;
    }




for(int i=0;i<nrV;i++){
newD[newS]=' ';
newS++;

}
 munmap(newD, newS);
}

int main(int argc, char **argv) {
    int fd;
    off_t size, i = 0, j = 0;
    char *data = NULL;
    
    if(argc != 2) {
        fprintf(stderr, "Usage: %s <file_name>\n", argv[0]);
        return 1;
    }

    fd = open(argv[1], O_RDWR);
    if(fd == -1) {
        perror("Could not open input file");
        return 1;
    }
    size = lseek(fd, 0, SEEK_END);
    lseek(fd, 0, SEEK_SET);

    data = (char*)mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if(data == (void*)-1) {
        perror("Could not map file");
        close(fd);
        return 1;
    }
    
 
delete(data,size,fd);
   

    munmap(data, size);
    data = NULL;
    close(fd);

    return 0;
}



