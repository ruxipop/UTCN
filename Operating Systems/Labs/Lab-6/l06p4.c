#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char **argv)
{
    
    if (argc !=2){
        
        perror("Nu sunt suficiente argumente");
        return -1;
    }
    else  if(argc == 2){  
        
        
        int  temp=-1;
        int status = 0;
        int pid = fork();
        
        
        
        if(pid == -1){
            perror("Could not create a child process");
            return -1;
        }
        else if(pid == 0){
            
            int fd = open(argv[1], O_RDONLY);
            if(fd == -1){
                perror("Could not open the file");
                exit(1);
            }
            
            
            temp = open("tmp", O_CREAT|O_TRUNC|O_WRONLY,0644);
            if(temp == -1){
                perror("Could not open the  temp file");
                exit(1);
            }
            
            dup2(temp, 1);
            execlp("grep", "grep", "abcd", argv[1], NULL);
            perror("Eroare");
            exit(2);
        }
        
        else{
            
            wait(&status);
            
        }
        
        
        pid = fork();
        
        if(pid == -1){
            perror("Could not open  child process");
            return -1;
        }
        else if(pid == 0){
            
            
            temp = open("tmp", O_RDONLY,0644);
            if(temp == -1){
                perror("Could not open the  temp file");
                return -1;
            }
            
            
            dup2(temp, 0);
            
            execlp("wc", "wc", "-l", NULL);
            
            perror("Eroare");
            exit(2);
        }
        else{
            
            wait(&status);
            
        }
    }
    
    
    
    
    return 0;
}
