
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
        int fd, fd2;
        int  size1=0;
        char c;
          int i;

        if (argc != 3)
        {
                perror("Numarul de argumente invalid");
                return -1;
        }
        fd = open(argv[1], O_RDONLY);
        if (fd == -1)
        {
               perror("Nu am putut deschide fisierul");
                return -1;
        }
        fd2 = creat(argv[2], S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH); //cream fisierul in care vom scri continutul fisierului fd inversat
        if (fd2 == -1)
        {
                perror("Nu am putut deschide fisierul");
                close(fd);
                return -1;
        }

        size1 = lseek(fd, 0, SEEK_END);//calculam dimensiunea fisierului fd
        lseek(fd, 0, SEEK_SET); //setam pe prima pozitie  cursorul in fd
         i = size1 - 1;
        while(i>=0)
    
        {
                
                if (read(fd, &c, 1)< 0)
                {
                        perror("Nu se poate citi fin fisierul");
                        break;
                }
                lseek(fd2, i, SEEK_SET);  //pe pozitia i din fisierul fd2 se va scrie caracterul citit din fd
                
                if(write(fd2, &c, 1) < 0)
                {
                        perror("Nu se poate scrie in fisier");
                        break;
                }
                i=i-1;
        }
       
        close(fd);
        close(fd2);
        return 0;
}
