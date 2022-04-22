#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>

off_t dirSize(const char *dirPath)
{
    DIR *dir = NULL;
    struct dirent *dir_entry = NULL;
    struct stat statbuf;
     char filePath[512];
    off_t size = 0;

    dir = opendir(dirPath);
    if(dir == NULL) {
        perror("Could not open directory");
        return -1;
    }
    while((dir_entry = readdir(dir)) != NULL) {
     
               if(strcmp(dir_entry->d_name, ".") != 0 && strcmp(dir_entry->d_name, "..") != 0) { //pentru evitarea buclei infinit
               snprintf(filePath, 512, "%s/%s", dirPath, dir_entry->d_name); // constructia caii
            if(lstat(filePath, &statbuf) == 0) {
                if(S_ISDIR(statbuf.st_mode)) {
                   size+= dirSize(filePath); //se apeleaza recursiv , pentru a parcurge si continutul subfolderelor
                }
                if(S_ISREG(statbuf.st_mode)) {
                    size += statbuf.st_size;
                }
                else if (S_ISLNK(statbuf.st_mode)){
                      size += 0;
                }
            }
            else {
                perror("eroare!\n");
            }
            }
        
    }
    closedir(dir);
    return size;
}

int main(int argc, char **argv)
{
    if(argc != 2) {
        fprintf(stderr, "Usage: %s <dir_name>\n", argv[0]);
        return 1;
    }
    if(dirSize(argv[1])!=-1){
    
     printf("A fost parcurs cu succes\n");
    printf("Dimensiune : %ld\n", dirSize(argv[1]));}
    else 
        printf("Nu a fost parcurs ,exista ceva erori!\n");
    return 0;
}
