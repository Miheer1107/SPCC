#include<stdio.h>
#include<string.h>
int m=0,n;
char z;
int i=0,j=0;
char f[10],a[10][10];
char ch;
void first(char c);
void follow(char c);


void first(char c)
{
	int k;
	if(!isupper(c))
	{
		f[m++]=c;
		
	}
		
	for(k=0; k<n; k++)
	{
		if(a[k][0]==c)
		{
			if(a[k][2]=='$')
				follow(a[k][0]);
			else if(islower(a[k][2]))
				f[m++] = a[k][2];
			else
				first(a[k][2]);
		}
	}
}

void follow(char c)
{
	if(a[0][0]==c)
		f[m++]='$';
	
	for(i=0; i<n; i++)
	{
		for(j=2; j<strlen(a[i]); j++)
		{
			if(a[i][j]==c)
			{
				if(a[i][j+1]!='\0')
					first(a[i][j+1]);
				if(a[i][j+1]=='\0' && c!=a[i][0])
					follow(a[i][0]);
			}
		}
	}
}



int main()
{
	
	printf("Enter the number of prod:\n");
	scanf("%d",&n);
	
	printf("Enter prod:\n");
	for(i=0; i<n; i++)
		scanf("%s",a[i]);
		
	printf("Enter char");
	scanf(" %c",&ch);
	do
	{
		//printf("Enter element:");
		//ch=getchar();
		//printf("Wait for scanf");
		
		//First
		m=0;
		first(ch);
		printf("First(%c) = {",ch);
		for(i=0; i<m; i++)
			printf("%c",f[i]);
		printf("}\n");
		strcpy(f,"");
		
		//Follow
		m=0;
		follow(ch);
		printf("Follow(%c) = {",ch);
		for(i=0; i<m; i++)
			printf("%c",f[i]);
		printf("}\n");
		strcpy(f,"");
		
		printf("Enter char");
		scanf(" %c",&ch);
	}while(z!='x');
	return(0);
}



























