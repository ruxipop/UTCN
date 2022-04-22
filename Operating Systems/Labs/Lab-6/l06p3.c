

#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>


//Alta varianta nu am gasit

int main(int argc, char **argv)
{
    int var = getpid();
    int fd;
    int count = 0;
    if((fd = creat("temp",0644)) <= 0)
    {
        perror("Can't create temporary file");
        exit(1);
    }
    close(fd);
    if((fd = open("temp",O_RDWR)) <=0)
    {
        perror("Can't open temporary file");
        exit(1);
    }
    // SCRIEM IN FISIER NUMARUL INITIAL
    write(fd,&count,sizeof(int));
    // SETAM INDEXUL PE PRIMA POZ DIN FISIER
    for(int i = 0; i <10;i++)
    {
        if(fork() == 0)
        {
            //ciim nr curent din fisier pt al incrementa cu 1
            read(fd,&count,sizeof(int));
            count++;
            // setam din nou indexul pe prima poz
            lseek(fd,0,SEEK_SET);
            //scriem nr din nou
            write(fd,&count,sizeof(int));	
            lseek(fd,0,SEEK_SET);
        }
        wait(NULL);
    }
    if(getpid() == var)
    {
        read(fd,&count,sizeof(int));
        printf("the number of created proccesses is: %d\n",count);
    }
    close(fd);
    unlink("temp");
    return 0;
}
