#include<stdio.h>
#include<string.h>
char expr[20], stack[20];
int top=0;
void push(char c)
{
	stack[++top]=c;
}
char pop()
{
	char data;
	data = stack[top--];
	return data;			
}

void main()
{
	int i;
	char x = 'A',op1,op2;
	printf("Enter postfix exp:\n");
	scanf("%s",expr);
	
	int len = strlen(expr);
	printf("%d",len);
	
	for(i=0; i<len; i++)
	{
		if(!(expr[i]== '*' || expr[i]=='/' || expr[i]=='+' || expr[i]=='-'))
			push(expr[i]);
		else
		{
			op2=pop();
			op1=pop();
			printf("\n%c\t\t%c\t\t%c\t\t%c\n",expr[i],op1,op2,x);
			push(x++);
		} 
	}
}
