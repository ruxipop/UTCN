#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>

void stergereD(const char *dirPath)
{
    DIR *dir = NULL;
    struct dirent *dir_entry = NULL;
    char filePath[512];
    struct stat statbuf;

    dir = opendir(dirPath);
    if(dir == NULL) {
        perror("Could not open directory");
    }
    while((dir_entry = readdir(dir)) != NULL) {
        if(strcmp(dir_entry->d_name, ".") != 0 && strcmp(dir_entry->d_name, "..") != 0) {
            snprintf(filePath, 512, "%s/%s", dirPath, dir_entry->d_name);
            if(stat(filePath, &statbuf) == 0) {
                if(S_ISDIR(statbuf.st_mode)) {
                    stergereD(filePath); //se apeleaza recursiv 
                  
                   
                }
                else {
                    unlink(filePath);  //daca este fisier se sterge asa
                }
            }
            else {
                perror("eroare!\n");
            }
        }
    }
    rmdir(dirPath); // se sterge folderul dat ca argument 
    closedir(dir); 
}

int main(int argc, char **argv)
{
    if(argc != 2) {
        fprintf(stderr, "Usage: %s <dir_name>\n", argv[0]);
        return 1;
    }
    stergereD(argv[1]);
    return 0;
}
