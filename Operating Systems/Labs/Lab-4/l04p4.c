#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[])
{
        int fd, pozitia,lungime;
        int i;
        int  r;
        char c;

        if (argc != 4)
        {
                perror("Numar argumente invalid");
                return -1;
        }
        fd = open(argv[1], O_RDWR); //deschidem in modul citire scriere
        if (fd == -1)
        {
                 perror("Nuu sa putut deschide fisierul");
                return -1;
        }
        if(sscanf(argv[3], "%d", &pozitia) != 1)
        {
                  perror("Numar invalid");
                
                return -1;
        }

        i= lseek(fd, 0, SEEK_END)-1;
        lungime=strlen(argv[2]);
  while(i>=pozitia)
       
        {
                lseek(fd, i, SEEK_SET);
                r = read(fd, &c, 1);
                if (r < 0)
                {
                        perror("Nu se poate citi din fisier");
                        break;
                }
                lseek(fd, i + lungime, SEEK_SET);
                r = write(fd, &c, 1);
                if(r < 0)
                {
                        perror("Nu se poate scrie in fisier");
                        break;
                }
                i=i-1;
        }
        lseek(fd, pozitia, SEEK_SET);
        r = write(fd, argv[2], lungime);
        if(r < 0)
        {
                perror("Nu se poate scrie in fisier");
               return -1;
        }

        
        close(fd);
        return 0;
}
