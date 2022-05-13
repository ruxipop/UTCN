member1(X, [X|_]).
member1(X, [_|T]) :- member(X, T).


append([], L2, L2).
append([H|T], L2, [H|CoadaR]) :- append(T, L2, CoadaR).

delete1(X, [X|T], T). 
delete1(X, [H|T], [H|R]) :- delete1(X, T, R).
delete1(_, [], []).  


delete_all(X, [X|T], R) :- delete_all(X, T, R). 
delete_all(X, [H|T], [H|R]) :- delete_all(X, T, R).
delete_all(_, [], []).

%Ex1
append3(L1,L2,L3,R):-append(L2,L3,R1),append(L1,R1,R).

%Ex2

add_first(X,L,[X|L]).

%Ex3


sum([H|T],R):- sum(T,R1),R is H+R1.
sum([],0).


%Ex4

separate_parity([H|T],[H|E],O):- 0 is H mod 2 ,separate_parity(T,E,O).
separate_parity([H|T],E,[H|O]):- 1 is H mod 2 ,separate_parity(T,E,O).
separate_parity([],[],[]).

%Ex 5

remove_duplicates([H|T],[H|R]):- \+ member(H,T),remove_duplicates(T,R).
remove_duplicates([H|T],R):- member(H,T),remove_duplicates(T,R).
remove_duplicates([],[]).
%Ex6


replace_all(X,Y,[X|T],[Y|R]):-replace_all(X,Y,T,R).
replace_all(X,Y,[H|T],[H|R]):-replace_all(X,Y,T,R).
replace_all(_,_,[],[]). 

%Ex7
drop_k_acc([H|T], K, R ,Acc):- Acc==1, drop_k_acc(T, K, R, K).
drop_k_acc([H|T], K, [H|R] ,Acc):- Acc>1, Acc2 is Acc-1, drop_k_acc(T, K, R, Acc2).
drop_k_acc([], _, [], _).
drop_k(L, K, R) :- drop_k_acc(L, K, R, K).