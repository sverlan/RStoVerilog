# Test with input and output and inner states
# @input A B C 
# @output  X Y Z
#- @initial X

A, ,P
B, ,Q
C, ,R

P Q, ,X
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
