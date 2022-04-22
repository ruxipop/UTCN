#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>




#define ALIGNMENT 3072



char pong_msg[5]={'\x04','P','O','N','G'};
char success_msg[8]={'\x07','S','U','C','C','E','S','S'};
char error_msg[6]={'\x05','E','R','R','O','R'};
char connect_msg[8]={'\x07','C','O','N','N','E','C','T'};


unsigned int variant = 67102;
char *memory_point = NULL;
unsigned int memory_size = 0;
int request_pipe=0;
int response_pipe=0;
char length = 0;
int field_length;
char *field, *filename;
int shared_name, file_fd;
char *file_point = NULL;
int file_size = 0;


int check_SF( char *fd)
{
    
    char magic[2];
    int header_size;
    char sect_type;
    int version;
    char no_of_sections=0;
    magic[2] = 0;
    memcpy(magic,fd , 1);
    memcpy(&header_size, fd +1, 2);
    
    if (strcmp(magic, "z") != 0)
    {
        printf("Bad magic\n");
        return -1;
    }
    memcpy(&version, fd +3, 2);
    
    if (!(98<=version && version <= 194 ))
    {
        printf("Bad version\n");
        return -1;
    }
    memcpy(& no_of_sections, fd +5, 1);
    if (!(8<=no_of_sections && no_of_sections <= 16 ))
    {
        printf("Bad no_of_sections\n");
        return -1;
    }
    
    
    for (int i = 0; i < no_of_sections; i++)
    {
        memcpy(&sect_type,fd+6+(i*(9+1+4+4))+9,4);
        if (sect_type != 21 && sect_type != 64 )
        {
            printf("Bad sect_type\n");
            return -1;
        }
        
    }
    return 0;}

int main()
{
    
    if (mkfifo("RESP_PIPE_67102", 0664) !=0)
    {
        perror("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
        return -1;
    }
    
    
    if (( request_pipe= open("REQ_PIPE_67102", O_RDONLY))< 0)
    {
        unlink("RESP_PIPE_67102");
        perror("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
        return -2;
    }
    
    
    if ((     response_pipe = open("RESP_PIPE_67102", O_WRONLY) )< 0)
    {
        unlink("RESP_PIPE_67102");
        perror("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
        return -2;
    }
    
    
    
    
    write(response_pipe,connect_msg,1);
    write(response_pipe,connect_msg+1,connect_msg[0]);
    printf("SUCCESS\n");
    
    
    while(1)
    {
        read(request_pipe, &length, sizeof( char));
        field_length = ( int)length;
        
        field = malloc(field_length + 1);
        field[field_length] = '\0';
        
        read(request_pipe, field, field_length);
        
        if (strcmp("PING", field) == 0)
        {
            write(response_pipe ,&length, 1);
            write(response_pipe, field, field_length);
            write(response_pipe, pong_msg, 5);
            write(response_pipe, &variant, sizeof(unsigned int));
            free(field);
        }
        
        
        else   if (strncmp(field,"CREATE_SHM", field_length) == 0)
        {
            int error= 0;
            
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            
            read(request_pipe, &memory_size, sizeof( int));
            
            
            shared_name = shm_open("/TlT4L05P", O_CREAT | O_RDWR, 0644);
            if (shared_name < 0)
            {perror("ERROR\ncannot get name of memeory");
                error= 1;
            }
            else
            {
                ftruncate(shared_name, memory_size);
                memory_point = (char *)mmap(0, memory_size, PROT_READ | PROT_WRITE, MAP_SHARED, shared_name, 0);
                if (memory_point == (void *)-1)
                {
                    
                    perror("ERROR\n  memeory ");
                    error = 1;
                }
            }
            
            if (error == 0)
            {
                write(response_pipe,success_msg,1);
                write(response_pipe,success_msg+1,success_msg[0]);
            }
            else
            {
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
            }
            
            close(shared_name);
            free(field);
        }
        
        
        
        
        else   if (strncmp(field,"WRITE_TO_SHM", field_length) == 0)
        {
            
            
            unsigned int offset;
            unsigned int value;
            
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            
            read(request_pipe, &offset, sizeof(unsigned int));
            read(request_pipe, &value, sizeof(unsigned int));
            if (memory_point != NULL && offset > 0 && (offset+sizeof(value)) < memory_size )
            {
                
                
                memcpy(memory_point+offset,&value,sizeof(unsigned int));
                
                
                write(response_pipe,success_msg,1);
                write(response_pipe,success_msg+1,success_msg[0]);
            }
            else
            {
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
            }
            free(field);
        }
        else   if (strcmp("MAP_FILE", field) == 0)
        {
            char filename_length;
            
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            
            read(request_pipe, &filename_length, 1);
            filename = malloc(1 + (int)filename_length);
            
            
            read(request_pipe, filename, (int)filename_length);
            filename[(int)filename_length] = 0;
            if ( (file_fd = open(filename, O_RDONLY))==-1)
            {
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
            }
            else
            {
                file_size = lseek(file_fd, 0, SEEK_END);
                lseek(file_fd, 0, SEEK_SET);
                
                
                file_point = ( char *)mmap(0, file_size, PROT_READ, MAP_SHARED, file_fd, 0);
                if (file_point == (void *)-1)
                {
                    write(response_pipe,error_msg,1);
                    write(response_pipe,error_msg+1,error_msg[0]);
                    close(file_fd);
                }
                else
                {
                    
                    
                    write(response_pipe,success_msg,1);
                    write(response_pipe,success_msg+1,success_msg[0]);
                }
            }
            free(filename);
            free(field);
        }
        
        
        else  if (strcmp("READ_FROM_FILE_OFFSET", field) == 0)
        {
            
            unsigned int offset, no_of_bytes;
            read(request_pipe, &offset, sizeof(unsigned int));
            read(request_pipe, &no_of_bytes, sizeof(unsigned int));
            
            
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            
            if ( file_point != NULL && memory_point != NULL && offset >0 && offset + no_of_bytes < file_size)
            {
                for(int i=0     ;i<no_of_bytes;i++){
                    memory_point[i]=file_point[offset+i];
                    
                }
                
                write(response_pipe,success_msg,1);
                write(response_pipe,success_msg+1,success_msg[0]);
                
            }
            else
            {
                
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
                
            }
            free(field);
        }
        
        
        else    if (strcmp("READ_FROM_FILE_SECTION", field) == 0)
        {
            int error=0;
            unsigned int section_no, offset, no_bytes;
            
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            read(request_pipe, &section_no, sizeof(unsigned int));
            
            
            read(request_pipe, &offset, sizeof(unsigned int));
            read(request_pipe, &no_bytes, sizeof(unsigned int));
            if (file_point == NULL || memory_point == NULL || check_SF(file_point) != 0){
                
                
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
                error=1;
                
                
            }
            if(error==0){
                unsigned char section_nb;
                memcpy(&section_nb,file_point+5,1);
                
                
                unsigned int section_offset;
                unsigned  int section_size;
                memcpy(&section_offset, file_point+ 6 + ((section_no-1)*(9+1+4+4)) + 10 , 4)    ;
                memcpy(&section_size, file_point + 6 +((section_no-1)*(9+1+4+4)) + 14, 4);
                
                
                if( section_no<1||section_no>section_nb||offset + no_bytes > section_size){
                    
                    
                    write(response_pipe,error_msg,1);
                    write(response_pipe,error_msg+1,error_msg[0]);
                    error=1;
                    
                }
                if(error==0){
                    for(int i=0     ;i<no_bytes;i++){
                        
                        
                        memory_point[i]=file_point[section_offset+offset+i];
                        
                        
                    }
                    write(response_pipe,success_msg,1);
                    write(response_pipe,success_msg+1,success_msg[0]);
                }
            }
            
            
            free(field);
        }
        
        
        else   if (strcmp("READ_FROM_LOGICAL_SPACE_OFFSET", field) == 0)
        {
            int error=0;
            char no_of_sections=0;
            unsigned int logical_offset=0;
            unsigned int no_of_bytes=0;
            unsigned int file_size=0;
            unsigned int file_offset=0;
            write(response_pipe,&length,1);
            write(response_pipe,field,length);
            
            read(request_pipe,&logical_offset,sizeof(unsigned int));
            read(request_pipe,&no_of_bytes,sizeof(unsigned int));
            
            
            if (file_point == NULL || memory_point == NULL ||check_SF(file_point) != 0)
            {
                write(response_pipe,error_msg,1);
                write(response_pipe,error_msg+1,error_msg[0]);
                error=1;
            }
            if(error==0){
                
                memcpy(&no_of_sections,file_point+5,1);
                
                
                for(int i = 0 ; i < no_of_sections; i++)
                
                
                {
                    memcpy(&file_offset, file_point+ 6 + (i*(9+1+4+4)) + 10 , 4)    ;
                    memcpy(&file_size, file_point + 6 +(i*(9+1+4+4)) + 14, 4);
                    
                    file_size = (int)((file_size +ALIGNMENT -1)/ALIGNMENT ) *ALIGNMENT   ;
                    
                    if(file_size < logical_offset)
                        
                        
                    {
                        logical_offset =   logical_offset-file_size;
                        
                    }
                    
                    
                    else
                        
                        
                    { for(int j = 0 ; j < no_of_bytes ; j++)
                        
                        {
                            memory_point[j] =  file_point[ file_offset + logical_offset + j];
                        }
                        
                        
                        write(response_pipe,success_msg,1);
                        write(response_pipe,success_msg+1,success_msg[0]);
                        
                        break;
                        
                        
                    }
                    
                }
                
            }
            
            free(field);
        }
        else if (strcmp("EXIT", field) == 0)
        {
            free(field);
            close(file_fd);
            close(shared_name);
            unlink(filename);
            munmap(memory_point, memory_size);
            munmap(file_point, file_size);
            shm_unlink("/TlT4L05P");
            unlink("RESP_PIPE_67102");
            return 0;
        }
        
    }
    return 0;
}






