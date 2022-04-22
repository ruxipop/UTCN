begin_version
3
end_version
begin_metric
1
end_metric
13
begin_variable
var0
-1
2
Atom neincalzit(cuptor)
NegatedAtom neincalzit(cuptor)
end_variable
begin_variable
var1
-1
2
Atom nu_in_cuptor(cuptor, aluat)
NegatedAtom nu_in_cuptor(cuptor, aluat)
end_variable
begin_variable
var2
-1
2
Atom nu_preparat_final(pizza)
NegatedAtom nu_preparat_final(pizza)
end_variable
begin_variable
var3
-1
2
Atom nu_are_amestec(aluat)
NegatedAtom nu_are_amestec(aluat)
end_variable
begin_variable
var4
-1
2
Atom nu_are_i2(masline1)
NegatedAtom nu_are_i2(masline1)
end_variable
begin_variable
var5
-1
2
Atom nu_are_i2(masline2)
NegatedAtom nu_are_i2(masline2)
end_variable
begin_variable
var6
-1
2
Atom nu_are_i2(masline3)
NegatedAtom nu_are_i2(masline3)
end_variable
begin_variable
var7
-1
2
Atom nu_are_i2(masline4)
NegatedAtom nu_are_i2(masline4)
end_variable
begin_variable
var8
-1
2
Atom nu_are_i1(branza1)
NegatedAtom nu_are_i1(branza1)
end_variable
begin_variable
var9
-1
2
Atom nu_are_i1(branza2)
NegatedAtom nu_are_i1(branza2)
end_variable
begin_variable
var10
-1
2
Atom nu_are_i3(salam)
NegatedAtom nu_are_i3(salam)
end_variable
begin_variable
var11
-1
2
Atom nu_are_i4(ciuperci)
NegatedAtom nu_are_i4(ciuperci)
end_variable
begin_variable
var12
-1
2
Atom nu_gata(pizza)
NegatedAtom nu_gata(pizza)
end_variable
0
begin_state
0
0
0
0
0
0
0
0
0
0
0
0
0
end_state
begin_goal
9
4 0
5 0
6 0
7 0
8 0
9 0
10 0
11 0
12 1
end_goal
26
begin_operator
amestec_ingrediente branza1 masline1 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 8 1 0
0 4 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza1 masline2 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 8 1 0
0 5 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza1 masline3 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 8 1 0
0 6 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza1 masline4 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 8 1 0
0 7 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza2 masline1 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 9 1 0
0 4 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza2 masline2 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 9 1 0
0 5 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza2 masline3 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 9 1 0
0 6 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
amestec_ingrediente branza2 masline4 ciuperci salam aluat pizza
1
2 1
5
0 3 0 1
0 9 1 0
0 7 1 0
0 10 1 0
0 11 1 0
0
end_operator
begin_operator
cumparare-ingredient1 branza1
0
1
0 8 0 1
2
end_operator
begin_operator
cumparare-ingredient1 branza2
0
1
0 9 0 1
4
end_operator
begin_operator
cumparare-ingredient2 masline1
0
1
0 4 0 1
1
end_operator
begin_operator
cumparare-ingredient2 masline2
0
1
0 5 0 1
4
end_operator
begin_operator
cumparare-ingredient2 masline3
0
1
0 6 0 1
1
end_operator
begin_operator
cumparare-ingredient2 masline4
0
1
0 7 0 1
8
end_operator
begin_operator
cumparare-ingredient3 salam
0
1
0 10 0 1
1
end_operator
begin_operator
cumparare-ingredient4 ciuperci
0
1
0 11 0 1
1
end_operator
begin_operator
gata_final cuptor aluat pizza
0
3
0 0 1 0
0 12 -1 1
0 1 1 0
0
end_operator
begin_operator
incalzire_cuptor cuptor aluat pizza
0
4
0 0 0 1
0 3 1 0
0 1 0 1
0 2 1 0
0
end_operator
begin_operator
start_reteta branza1 masline1 ciuperci salam pizza
4
8 1
4 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza1 masline2 ciuperci salam pizza
4
8 1
5 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza1 masline3 ciuperci salam pizza
4
8 1
6 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza1 masline4 ciuperci salam pizza
4
8 1
7 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza2 masline1 ciuperci salam pizza
4
9 1
4 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza2 masline2 ciuperci salam pizza
4
9 1
5 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza2 masline3 ciuperci salam pizza
4
9 1
6 1
10 1
11 1
1
0 2 0 1
0
end_operator
begin_operator
start_reteta branza2 masline4 ciuperci salam pizza
4
9 1
7 1
10 1
11 1
1
0 2 0 1
0
end_operator
0