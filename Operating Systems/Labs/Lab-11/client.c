#include <sys/types.h>
#include<stdlib.h>
#include <sys/stat.h>
#include<stdio.h>
#include <fcntl.h>
#include<sys/wait.h>
#include <unistd.h> 


int main(void)
{int fd_write,fd_read;
	int a,b,result;
	char operator;
	
	
	
	mkfifo("operation",0600);
	mkfifo("result",0600);
		if(fork() == 0)
	{
		execlp("./server","server",NULL);
		perror("Cannot start server");
		exit(1);
	}
	
	
	if((fd_read = open("result",O_RDONLY)) < 0)		
	{
		perror("Cannot open pipe");
		exit(1);
	}
	if((fd_write = open("operation",O_WRONLY)) < 0)		
	{
		perror("Cannot open pipe");
		exit(1);
	}

	
	
	printf("Introdu operatia:");
	scanf("%d %c %d",&a,&operator,&b);
	while(1)
	{
		write(fd_write,&a,sizeof(a));
		write(fd_write,&operator,sizeof(operator));
		write(fd_write,&b,sizeof(b));
		read(fd_read,&result,sizeof(result));
		printf("rezultat = %d\n",result);
		printf("Introdu operatia:");
		scanf("%d %c %d",&a,&operator,&b);
	}

	
	close(fd_write);
	close(fd_read);
	unlink("operation");
	unlink("result");
	return 0;
}


