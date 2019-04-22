# Testing intersection of input and output symbols with default behavior
#- @input A B C 
#= @output  X Y Z
#- @initial X

A, ,P
B, ,Q
C, ,R

A P Q,B C,X A C 
P R, ,Y
R Q, C, Z 

---

X Y Q
A
B
C
.
A B C
A
A
A
B
B
B
C
C
C

