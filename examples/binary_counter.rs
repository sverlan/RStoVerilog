# Binary counter RS
# @input e0
# @output e1 e2 e3 e4
#- @initial e0

# Reactions maintaining the presence of a bit in a current state.
e1, e0, e1

e2, e0, e2
e2, e1, e2

e3, e0, e3
e3, e1, e3
e3, e2, e3

e4, e0, e4
e4, e1, e4
e4, e2, e4
e4, e3, e4

# Reactions actually doing the increment.
e0,           e1, e1
e0 e1,        e2, e2
e0 e1 e2,     e3, e3
e0 e1 e2 e3,  e4, e4

---
e0
e0
.
e0
