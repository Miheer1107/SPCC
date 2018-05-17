%{
#include <stdio.h>
%}
%%

"//".* {}
boolean|int|float|class|public|main|static|void|for|if|args|String|char|do|double|else|extends|final|import|interface|new|private|super|this|try printf("\nKeyword  %s", yytext);
";" printf("\ndelim");
"," printf("\nseparator");
[0-9]+\.[0-9]+ printf("\nfloat_constant %s", yytext);
\".*\" printf("\nstr_constant %s", yytext);
[0-9]+ printf("\nint_const %s", yytext);
"=" printf("\nassign_op %s", yytext);
["+""-""/""*""%"]+ printf("\nartihmetic_op %s", yytext);
["<=""=="">="">""<""!="] printf("\nrel_op %s", yytext);
[a-zA-Z_][a-zA-Z_0-9]* printf("\nid %s", yytext);
\. printf("\ndot_access");
"{" printf("\nbrace_open %s", yytext);
"}" printf("\nbrace_close %s", yytext);
"(" printf("\nparanthesis_open %s", yytext);
")" printf("\nparanthesis_open %s", yytext);

%%

main(){
    yylex();
}