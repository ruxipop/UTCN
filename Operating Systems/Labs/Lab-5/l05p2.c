#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>



int cautareD(const char* dirPath,char * fisierName , char *string){

static int i=0;
   DIR *dir = NULL;
    struct dirent *dir_entry = NULL;
    struct stat statbuf;
     char filePath[512];
        int fd=-1;
     char *buffer=NULL;
     int size=0;
  

	
    dir = opendir(dirPath);
    if(dir == NULL) {
        perror("Could not open directory");
        return -1;
    }
   while((dir_entry = readdir(dir)) != NULL) {
     
               if(strcmp(dir_entry->d_name, ".") != 0 && strcmp(dir_entry->d_name, "..") != 0) { //pentru evitarea buclei infinit
               snprintf(filePath, 512, "%s/%s", dirPath, dir_entry->d_name); // constructia caii
               if(lstat(filePath, &statbuf) == 0) {
             
                if(S_ISREG(statbuf.st_mode)) {
                    if(strstr(dir_entry->d_name,fisierName)){
                       fd=open(filePath,O_RDONLY);
                       if(fd==-1){
                       perror("Nu am putut deschide fisierul in modul citire");
                       return -1;
                       }
                     size = lseek(fd, 0, SEEK_END); 
                     
                     buffer = (char*)malloc(size * sizeof(char));
                   	 if(buffer == NULL) {
		                      printf("alocare nereusita\n");
		                 return -1;
	                      }                       
	             lseek(fd,0,SEEK_SET);
                       if(read(fd,buffer,4000)<0)  { //poate citi atatea caractere
                        perror("Nu am putut citi din fisier");
                       return -1;
                    }
                     if(strstr(buffer,string)){
                       char linkP[512];
                       snprintf(linkP, 512, "./%s.%d", fisierName,i); // constructia caii personalizate
                       i++;
     
                      if(symlink(filePath,linkP)==-1){
                        perror("Nu sa creat link-ul");
                       break;
                      
                     
                     }
                    }
                       close(fd);
                     free(buffer);
                }
                
                
           
              }
                 if(S_ISDIR(statbuf.st_mode)) {
                cautareD(filePath,fisierName,string);
              
            }
            }
           else {
                perror("eroare!\n");
            }
            }
        
    }
closedir(dir);

return 0;

}




int main(int argc, char **argv)

{
    if(argc != 4) {
        printf("numar agumente gresit");
        return 1;
    }
    
     if(cautareD(argv[1],argv[2],argv[3])==-1){
    
  
        printf("Nu a fost parcurs ,exista ceva erori!\n");}
 
    
    return 0;
}





