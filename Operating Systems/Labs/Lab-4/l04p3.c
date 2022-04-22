#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main(int arc, char *argv[])
{
        int fd, fd2, dim = 0;
        char c;

        if (arc != 3)
        {
                perror("Prea multe argumente");
                return -1;
        }
        fd = open(argv[1], O_RDONLY);
        if (fd == -1)
        {
                perror("nu se poate deschide fisierul");
                return -1;
        }
        fd2 = creat(argv[2], S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
        if (fd2 == -1)
        {
                perror("nu se poate deschide fisierul");
                close(fd);
                return -1;
        }

        int i= lseek(fd, 0, SEEK_END)-1;

       while(i>=0)
   
        {
                lseek(fd, i, SEEK_SET);
               
                if (read(fd, &c, 1)!=1)
                {
                        perror("nu se poate citi din fisier");
                          break;
                }
                if(c == '\n')
                {
                        for(int i = 0; i < dim; ++i)
                        {
                                
                                if (read(fd, &c, 1)!=1)
                                {
                                        perror("nu se poate citi din fisier");
                                                    break;
                                }
                                
                                if(write(fd2, &c, 1)!=1)
                                {
                                        perror("nu se poate scrie in fisier");
                                                  break;
                                }
                        }
                        c = '\n';
                        
                        if(write(fd2, &c, 1)!=1)
                        {
                                perror("nu se poate scrie in fisier");
                                                 break;
                                                 }
                        dim = 0;
                }
                else{
                        dim++;}
                        i=i-1;
        }
      //nu am stiut sa continui
        close(fd);
        close(fd2);
        return 0;
}
