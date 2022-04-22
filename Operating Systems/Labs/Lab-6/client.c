#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include<string.h>

int main(int argc, char **argv)
{
    int pid;
    int status = 0;
    int op1,op2;
    char op;
    char oper1[12],oper2[12];
    
    printf(">");
    scanf("%d %c %d",&op1,&op,&op2);
    snprintf(oper1,12,"%d",op1);
    snprintf(oper2,12,"%d",op2);
    
    pid = fork();
    switch(pid)
    {
        case -1:
            perror("Can't create child");
            exit(1);
            break;
        case 0: // child
            printf("[Child] Starting server...\n");
            execl("./server","server",oper1,&op,oper2,NULL);
            printf("Can't start server\n");
            exit(1);
            break;
            
        default : //parent
            waitpid(pid,&status,0);
            printf("[Parent]\nThe result is:%d\n",WEXITSTATUS(status));
            break;			
    }
    
    
    return 0;
}
