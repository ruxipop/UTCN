#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#define MAX 100

//metoda pe care ne ati aratat-o
int getLine(int fd, int lineNr, char *line, int max_length) {
	char *buffer = NULL;
	int size, lineIndex = 0, bufferPos = 0, charIndex = 0;
	int returnValue = -1;
	size = lseek(fd, 0, SEEK_END); // dimensiunea fisierului
	if(size == -1) {
		printf("nu am reusit sa fac lseek la final\n");
		return -1;
	}
	buffer = (char*)malloc(size * sizeof(char));
	if(buffer == NULL) {
		printf("alocare nereusita\n");
		return -1;
	}
	if(lseek(fd, 0, SEEK_SET) == -1) {
		printf("eroare linia de inceput");
		free(buffer);
		return -1;
	}
	if(read(fd, buffer, size) != size) {
		printf("nu am reusit sa citesc tot fisierul");
		free(buffer);
		return -1;
	}
	
	while(bufferPos < size) {
		if(lineIndex + 1 == lineNr) {
			if(charIndex < max_length) {
				if(buffer[bufferPos] == '\n') {
					line[charIndex] = 0;
					returnValue = 0;
					break;
				}
				line[charIndex] = buffer[bufferPos]; 
				charIndex++;
			} else {
				printf("linie gasita, prea multe caractere\n");
				return -1;
				}
			} else { 
				if(buffer[bufferPos] == '\n') {
					lineIndex++;
				}
			}
			bufferPos++;
		}
		
	free(buffer);
	return returnValue;		
}

int main(int argc, char** argv) {
	char line[100];
	int fd, lineNr;
	
	fd = open(argv[1], O_RDONLY);
	if(fd == -1) {
		printf("nu am deschis fisierul");
		return -1;
	}
	
	lineNr = atoi(argv[2]);
	if(getLine(fd, lineNr, line, MAX) != 0) {
		printf("nu sa citit linia \n");
	} else {
		printf("linia %d :%s\n", lineNr, line);
	}
	close(fd);
	return 0;
}
