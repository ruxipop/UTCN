perm_sort(L,R):-perm(L, R), is_ordered(R), !.
is_ordered([H1, H2|T]):-H1 =< H2, is_ordered([H2|T]).
is_ordered([_]). % daca ii doar un element ii deja ordonata

perm(L, [H|R]):-append(A, [H|T], L), append(A, T, L1), perm(L1, R). 
perm([], []).

min1([H|T],M):- min1(T,M),M<H,!.
min1([H|_],H).


%sortare_prin_selectie
sel_sort(L, [M|R]):- min1(L, M), delete1(M, L, L1), sel_sort(L1, R). 
sel_sort([], []).

%sortare_prin_insertie

ins_sort([H|T], R):- ins_sort(T, R1), insert_ord(H, R1, R). 
ins_sort([], []).

insert_ord(X, [H|T], [H|R]):-X>H, !, insert_ord(X, T, R).
insert_ord(X, T, [X|T]).

%sortare_bulelor
bubble_sort(L,R):-one_pass(L,R1,F), nonvar(F), !, bubble_sort(R1,R). 
bubble_sort(L,L).

one_pass([H1,H2|T], [H2|R], F):-H1>H2, !, F=1, one_pass([H1|T],R,F). 
one_pass([H1|T], [H1|R], F):-one_pass(T, R, F).
one_pass([], [] ,_).

%sortare_rapida
quick_sort([H|T], R):- % alegem pivot primul element
          partition(H, T, Sm, Lg),
          quick_sort(Sm, SmS), % sortam sublista cu elementele mai mici decât pivotul
          quick_sort(Lg, LgS), % sortam sublista cu elementele mai mari  decât pivotul
          append(SmS, [H|LgS], R). 
quick_sort([], []).
partition(H, [X|T], [X|Sm], Lg):-X<H, !, partition(H, T, Sm, Lg). 
partition(H, [X|T], Sm, [X|Lg]):-partition(H, T, Sm, Lg). 
partition(_, [], [], []). 

%sortare_prin_interclasare

merge_sort(L, R):-
     split(L, L1, L2), % împarte L în doua subliste de lungimi egale 
     merge_sort(L1, R1),
     merge_sort(L2, R2),
     merge(R1, R2, R). % interclasează sublistele ordonate

merge_sort([H], [H]). % split returnează fail dacă lista ii vidă sau are doar un singur element
merge_sort([], []).

split(L, L1, L2):- 
    length(L, Len),
    Len>1,
    K is Len/2, 
    splitK(L, K, L1, L2).

splitK([H|T], K, [H|L1], L2):-K>0,!,K1 is K-1,splitK(T, K1, L1, L2). 
splitK(T, _, [], T).

merge([H1|T1], [H2|T2], [H1|R]):-H1<H2, !, merge(T1, [H2|T2], R). 
merge([H1|T1], [H2|T2], [H2|R]):-merge([H1|T1], T2, R).
merge([], L, L).
merge(L, [], L).

max1([H|T],M):- max1(T,M),M>H,!.
max1([H|_],H).

%lab5 ex1

delete1(H, [H|L], L) .
delete1(X, [H|L], [H|R]) :- delete1(X, L, R).

perm1(L,[X|R]):-delete1(X,L,Ri),perm1(Ri,R).
perm1([],[]).

%lab5 ex2


sel_sort1(L, [M|R]):- max1(L, M), delete1(M, L, L1), sel_sort1(L1, R). 
sel_sort1([], []).

%lab5 ex3

min_char([H|T], M) :- min_char(T, M),char_code(H, R), char_code(M, Mx), Mx<R,!.
min_char([H|_], H).  %similar cu min de mai sus doar ca le-am codificat din caracter cu char_code


sort_chars_min(L, [M|R]):- min_char(L, M), delete1(M, L, L1), sort_chars_min(L1, R). 
sort_chars_min([], []).

max_char([H|T], M) :- max_char(T, M),char_code(H, R), char_code(M, Mx), Mx>R,!.
max_char([H|_], H).  %similar cu max de mai sus


sort_chars_max(L, [M|R]):- max_char(L, M), delete1(M, L, L1), sort_chars_max(L1, R). 
sort_chars_max([], []).


%lab5 ex4


one_pass1([H1,H2|T], [H2|R], F):- length(H1, R1), length(H2, R2), R1 >R2, !, F=1, one_pass1([H1|T],R,F).
one_pass1([H1|T], [H1|R], F):-one_pass1(T, R, F).
one_pass1([], [] ,_).

sort_len(L,R):-one_pass1(L,R1,F),nonvar(F),!,sort_len(R1,R).
sort_len(L,L).


insert_ord1(X,[H|T],[H|R]):-length(X,X1),length(H,H1),X1>H1,!, insert_ord1(X,T,R).
insert_ord1(X,T,[X|T]).

ins_sort1([H|T],R):-ins_sort1(T,R1),insert_ord1(H,R1,R).
ins_sort1([],[]).

