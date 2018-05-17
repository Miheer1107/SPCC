#include<stdio.h>

void main()
{
	int i,j,n;
	int index=3;
	char prod[10][10];
	char start;
	printf("Enter number of prod\n");
	scanf("%d",&n);
		
	printf("Enter prod as E->E+A\n");
	for(i=0; i<n; i++)
		scanf("%s",prod[i]);
	
	for(i=0; i<n; i++)
	{
		printf("Grammar: %s\n",prod[i]);
		start = prod[i][0];
		char alpha[10], beta[10];
		int k=0, l=0, index=3;
		
		if(start==prod[i][index])
		{
			printf("Grammar is left recursive\n");
			index++;
			while(prod[i][index]!='|' && prod[i][index]!='\0')
				alpha[k++] = prod[i][index++];

			alpha[k]='\0';
			index++;
			
			if(prod[i][index]=='\0')
				printf("Expression cannot be reduced\n");
			else
			{
				while(prod[i][index]!='\0')
					beta[l++] = prod[i][index++];
					
				beta[l]='\0';	
				printf("Reduced grammar: \n");
				printf("%c->%s%c\'\n",start,beta,start);
				printf("%c\'->%s%c\' | ^\n",start,alpha,start);
			}
		}
	}
}
