#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

typedef struct _FLAGS
{
	int list,parse,extract,findall,
			recursive,size_greater,name_ends_with,
			path,recursion_first_pass;
}FLAGS;

char*read_magic(int fd)
{
	char*res=(char*)calloc(3,sizeof(char));
	if(!res)
	{
		exit(-1);
	}
	lseek(fd,-2,SEEK_END);
	read(fd,res,2);
	return res;
}

u_int16_t read_size(int fd)
{
	u_int16_t res;
	lseek(fd,-4,SEEK_END);
	read(fd,&res,2);
	return res;
}

u_int8_t read_no_of_sections(int fd)
{
	u_int8_t res;
	u_int16_t size=read_size(fd);
	lseek(fd,-size+2,SEEK_END);
	read(fd,&res,1);
	return res;
}

u_int16_t read_version(int fd)
{
	u_int16_t res,size=read_size(fd);
	lseek(fd,-size,SEEK_END);
	read(fd,&res,2);
	return res;
}

u_int32_t read_sect_size(int fd,int sect_no)
{
	u_int32_t res;
	u_int8_t no_of_sections=read_no_of_sections(fd);
	lseek(fd,-4-(no_of_sections-sect_no+1)*20+16,SEEK_END);
	read(fd,&res,4);
	return res;
}

u_int32_t read_sect_offset(int fd,int sect_no)
{
	u_int32_t res;
	u_int8_t no_of_sections=read_no_of_sections(fd);
	lseek(fd,-4-(no_of_sections-sect_no+1)*20+12,SEEK_END);
	read(fd,&res,4);
	return res;
}

u_int8_t read_sect_type(int fd,int sect_no)
{
	u_int8_t res;
	u_int8_t no_of_sections=read_no_of_sections(fd);
	lseek(fd,-4-(no_of_sections-sect_no+1)*20+11,SEEK_END);
	read(fd,&res,1);
	return res;
}

char*read_sect_name(int fd,int sect_no)
{
	char*c=(char*)calloc(12,sizeof(char));
	if(!c)
	{
		exit(-1);
	}
	u_int8_t no_of_sections=read_no_of_sections(fd);
	lseek(fd,-4-(no_of_sections-sect_no+1)*20,SEEK_END);
	read(fd,c,11);
	return c;
}

void list(FLAGS*f,char*path,char*name_ends_with,int size_greater)
{
	DIR*d=opendir(path);
	if(!d)
	{
		printf("ERROR\nwrong directory path\n");
		exit(-1);
	}
	if(f->recursion_first_pass==1||f->recursive==0)
	{
		printf("SUCCESS\n");
		f->recursion_first_pass=0;
	}
	char file_path[1024];
	struct stat statbuf;
	struct dirent*entry;
	while(1)
	{
		entry=readdir(d);
		if(!entry)
		{
			break;
		}
		if(f->name_ends_with)
		{
			if(strcmp(entry->d_name+strlen(entry->d_name)-strlen(name_ends_with),name_ends_with)!=0)
			{
				continue;
			}
		}
		snprintf(file_path,1024,"%s/%s",path,entry->d_name);
		if(lstat(file_path,&statbuf)==0)
		{
			if(S_ISREG(statbuf.st_mode))
			{
				if(f->size_greater)
				{
					int fd=open(file_path,O_RDONLY);
					if(fd==-1)
					{
						exit(-1);
					}

					if(lseek(fd,0,SEEK_END)<=size_greater)
					{
						close(fd);
						continue;
					}
					close(fd);
				}
				printf("%s\n",file_path);
			}
			else
			{
				if(S_ISDIR(statbuf.st_mode))
				{
					if(!strcmp("..",entry->d_name)||!strcmp(".",entry->d_name))
					{
						continue;
					}
					if(!f->size_greater)
					{
						printf("%s\n",file_path);
					}
					if(f->recursive)
					{
						list(f,file_path,name_ends_with,size_greater);
					}
				}
			}
		}
	}
	closedir(d);
}

void parse(char*path)
{
	int fd=open(path,O_RDONLY);
	if(fd==-1)
	{
		exit(-1);
	}

	char*magic=read_magic(fd);
	if(strcmp(magic,"rg"))
	{
		printf("ERROR\nwrong magic\n");
		free(magic);
		close(fd);
		exit(-1);
	}
	free(magic);

	u_int16_t version=read_version(fd);
	if(version<86||version>178)
	{
		printf("ERROR\nwrong version\n");
		close(fd);
		exit(-1);
	}

	u_int8_t no_of_sections=read_no_of_sections(fd);
	if(no_of_sections<5||no_of_sections>19)
	{
		printf("ERROR\nwrong sect_nr\n");
		close(fd);
		exit(-1);
	}

	for(int i=1;i<=no_of_sections;i++)
	{
		u_int8_t sect_type=read_sect_type(fd,i);
		if(sect_type!=95&&sect_type!=89&&sect_type!=83)
		{
			printf("ERROR\nwrong sect_types\n");
			close(fd);
			exit(-1);
		}
	}

	printf("SUCCESS\n");
	printf("version=%d\n",version);
	printf("nr_sections=%d\n",no_of_sections);
	for(int i=1;i<=no_of_sections;i++)
	{
		char*name=read_sect_name(fd,i);
		printf("section%d: %s %d %d\n",i,name,read_sect_type(fd,i),read_sect_size(fd,i));
		free(name);
	}
	close(fd);
}

int is_section_file(int fd)
{
	char*magic=read_magic(fd);
	if(strcmp(magic,"rg"))
	{
		free(magic);
		close(fd);
		return 0;
	}
	free(magic);

	u_int16_t version=read_version(fd);
	if(version<86||version>178)
	{
		close(fd);
		return 0;
	}

	u_int8_t no_of_sections=read_no_of_sections(fd);
	if(no_of_sections<5||no_of_sections>19)
	{
		close(fd);
		return 0;
	}

	for(int i=1;i<=no_of_sections;i++)
	{
		u_int8_t sect_type=read_sect_type(fd,i);
		if(sect_type!=95&&sect_type!=89&&sect_type!=83)
		{
			close(fd);
			return 0;
		}
	}

	return 1;
}

void extract(char*path,int sect_nr,int line_nr)
{
	int fd=open(path,O_RDONLY);
	if(fd==-1)
	{
		exit(-1);
	}
	if(!is_section_file(fd))
	{
		printf("ERROR\ninvalid file\n");
		close(fd);
		exit(-1);
	}
	if(sect_nr>read_no_of_sections(fd))
	{
		printf("ERROR\ninvalid section\n");
		close(fd);
		exit(-1);
	}

	u_int32_t sect_size=read_sect_size(fd,sect_nr);
	u_int32_t offset=read_sect_offset(fd,sect_nr);

	char s[sect_size];
	lseek(fd,offset,SEEK_SET);
	read(fd,s,sect_size);

	u_int32_t line_counter=1;
	int line_found=0;
	for(u_int32_t i=sect_size-1;i>=0;i--)
	{
		if((s[i]==0xA||s[i]==0xD)&&i!=0&&i!=sect_size-1&&s[i+1]!=0xA&&s[i+1]!=0xD)
		{
			line_counter++;
			i--;
			if(line_counter>line_nr)
			{
				close(fd);
				return;
			}
		}
		if(line_counter==line_nr)
		{
			if(line_found==0)
			{
				printf("SUCCESS\n");
				line_found=1;
			}
			printf("%c",s[i]);
		}
	}
	if(line_counter!=line_nr)
	{
		printf("ERROR\ninvalid line\n");
	}
	close(fd);
}

int has13pluslines(int fd)
{
	u_int8_t no_of_sections=read_no_of_sections(fd);
	for(int i=1;i<=no_of_sections;i++)
	{
		int count=1;
		u_int32_t sect_size=read_sect_size(fd,i);
		u_int32_t offset=read_sect_offset(fd,i);
		lseek(fd,offset,SEEK_SET);
		char s[sect_size];
		read(fd,s,sect_size);
		for(int i=0;i<sect_size;i++)
		{
			if((s[i]==0xA||s[i]==0xD)&&i!=0&&i!=sect_size-1&&s[i+1]!=0xA&&s[i+1]!=0xD)
			{
				count++;
				if(count==14)
				{
					return 1;
				}
			}
		}
	}
	return 0;
}

void findall(FLAGS*f,char*path)
{
	DIR*d=opendir(path);
	if(!d)
	{
		printf("ERROR\nwrong directory path\n");
		exit(-1);
	}
	if(f->recursion_first_pass==1)
	{
		printf("SUCCESS\n");
		f->recursion_first_pass=0;
	}
	char file_path[1024];
	struct stat statbuf;
	struct dirent*entry;
	while(1)
	{
		entry=readdir(d);
		if(!entry)
		{
			break;
		}
		snprintf(file_path,1024,"%s/%s",path,entry->d_name);
		if(lstat(file_path,&statbuf)==0)
		{

			if(S_ISREG(statbuf.st_mode))
			{
				int fd=open(file_path,O_RDONLY);
				if(is_section_file(fd)&&has13pluslines(fd))
				{
					printf("%s\n",file_path);
				}
				fflush(stdout);
				close(fd);
			}
			else
			{
				if(S_ISDIR(statbuf.st_mode))
				{
					if(!strcmp("..",entry->d_name)||!strcmp(".",entry->d_name))
					{
						continue;
					}
					findall(f,file_path);
				}
			}
		}
	}
	closedir(d);
}

int main(int argc,char**argv)
{
	if(argc==2)
	{
		if(!strcmp("variant",argv[1]))
		{
			printf("88475");
		}
	}

	FLAGS f;
	FLAGS*flags=&f;
	memset(flags,0,sizeof(FLAGS));
	if(!flags)
	{
		return -1;
	}

	char name_ends_with[256];
	char size_greater[256];
	char path[256];
	char line_str[256];
	char section_str[256];
	if(argc>=3)
	{
		for(int i=0;i<argc;i++)
		{
			if(!strcmp(argv[i],"list"))
			{
				flags->list=1;
				continue;
			}
			if(!strcmp(argv[i],"parse"))
			{
				flags->parse=1;
				continue;
			}
			if(!strcmp(argv[i],"extract"))
			{
				flags->extract=1;
				continue;
			}
			if(!strcmp(argv[i],"findall"))
			{
				flags->findall=1;
				flags->recursion_first_pass=1;
				continue;
			}
			if(!strcmp(argv[i],"recursive"))
			{
				flags->recursive=1;
				flags->recursion_first_pass=1;
				continue;
			}
			if(!strncmp(argv[i],"name_ends_with=",strlen("name_ends_with=")))
			{
				flags->name_ends_with=1;
				strcpy(name_ends_with,argv[i]+strlen("name_ends_with="));
			}
			if(!strncmp(argv[i],"size_greater=",strlen("size_greater=")))
			{
				flags->size_greater=1;
				strcpy(size_greater,argv[i]+strlen("size_greater="));
			}
			if(!strncmp(argv[i],"path=",strlen("path=")))
			{
				flags->path=1;
				strcpy(path,argv[i]+strlen("path="));
			}
			if(!strncmp(argv[i],"line=",strlen("line=")))
			{
				strcpy(line_str,argv[i]+strlen("line="));
			}
			if(!strncmp(argv[i],"section=",strlen("section=")))
			{
				strcpy(section_str,argv[i]+strlen("section="));
			}
		}
	}
	if(flags->path==0)
	{
		return -1;
	}
	if(flags->list)
	{
		int size_greater_n;
		sscanf(size_greater,"%d",&size_greater_n);
		list(flags,path,name_ends_with,size_greater_n);
		return 0;
	}
	if(flags->parse)
	{
		parse(path);
		return 0;
	}
	if(flags->extract)
	{
		int sect_nr,line_nr;
		sscanf(section_str,"%d",&sect_nr);
		sscanf(line_str,"%d",&line_nr);
		extract(path,sect_nr,line_nr);
		return 0;
	}
	if(flags->findall)
	{
		findall(flags,path);
		return 0;
	}
	return 0;
}
