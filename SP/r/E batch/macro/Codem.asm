MACRO
&LAB ADD &A1
&LAB A 1 , &A1
MEND
MACRO
&A0 ADDS &A1 &A2
&A0 A 2 , &A1
ST 2 , &A2
MEND
PRGM START 0
USING * , 15
LOOP1 ADD D1
LOOP2 ADDS D2 D3
ST 1,3
END