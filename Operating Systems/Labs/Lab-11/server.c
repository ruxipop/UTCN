#include <sys/types.h>
#include<stdlib.h>
#include <sys/stat.h>
#include<stdio.h>
#include <fcntl.h>
#include<sys/wait.h>
#include <unistd.h> 


int main(void)
{
	int a,b;
	char operator;
	int result;

	
	int fd_write,fd_read;
	if((fd_read = open("operation",O_RDONLY)) < 0)		
	{
		perror("Cannot open pipe");
		exit(1);
	}	

	
	if((fd_write = open("result",O_WRONLY)) < 0)		
	{
		perror("Cannot open pipe");
		exit(1);
	}	

	
	read(fd_read,&a,sizeof(a));
	read(fd_read,&operator,sizeof(operator));
	read(fd_read,&b,sizeof(b));
	while(1)
	{
		switch(operator)
		{
			case '+':
				result = a + b;
				break;
			case '-':
				result = a - b;
				break;
		  default : result=0;
		   break;
		}
		write(fd_write,&result,sizeof(result));
		read(fd_read,&a,sizeof(a));
		read(fd_read,&operator,sizeof(operator));
		read(fd_read,&b,sizeof(b));
	}
	close(fd_read);
	close(fd_write);
	return 0;
}
