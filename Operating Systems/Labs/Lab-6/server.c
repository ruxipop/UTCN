#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include<string.h>

int main(int argc, char **argv)
{
	int op1,op2;
	int result;
	op1 = atoi(argv[1]);
	op2 = atoi(argv[3]);
	printf("Server started.\n[Child] the operands are :%d and %d\n",op1,op2);
	switch(argv[2][0])
	{
		case '+':
			result = op1 + op2;
			printf("The result is %d\n",result);
			exit(result);
			break;
		case '-':
			result = op1-op2;
			printf("The result is %d\n",result);
			exit(result);
			break;
		default:
			printf("Invalid operation\n");
			exit(1);
			break;
	}
	return 0;
}
