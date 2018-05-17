MACRO
ADD	&arg
	L	1 , &arg
	A	1 , =F'10'
	ST	1 , &arg
MEND

MACRO
INCR	&arg1 , &arg2 , &arg3
A 	1 , &arg1
A 	2 , &arg2
A 	3 , &arg3
MEND

PGM2 	START 	0
		USING 	* , 15
AC 		EQU 	2
INDEX 	EQU 	3
		ADD	DATA1
TOTAL 	EQU 	4
		LR 	1 , TOTAL
		INCR DATA1 , DATA2 , DATA3
DATA1	DC	F'5'
DATA2	DC	F'6'
DATA3	DC	F'7'
		END
