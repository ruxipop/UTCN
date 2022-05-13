member1(X,[X|_]):-!.
member1(X,[_|T]):- member1(X,T).

delete1(X,[X|T],T):-!.
delete1(X,[H|T],[H|R]):-delete1(X,T,R).
delete1(_,[],[]).

%Lungime
%recursivitate inapoi
length1([H|T],Len):- length1(T,Lcoada),Len is 1+Lcoada.
length1([],0).

%recursivitate inainte
length2([H|T],Acc,Len) :- Acc1 is Acc+1 ,length2(T,Acc1,Len).
length2([],Acc,Acc).
length2_pretty(L,R):-length2(L,0,R).

%Inversa
%recursivitate inapoi
reverse1([],[]).
reverse1([H|T],R):- reverse1(T,Rcoada),append(Rcoada,[H],R).

%recursivitate inainte
reverse2([],Acc,Acc).
reverse2([H|T],Acc,R):- Acc1=[H|Acc],reverse2(T,Acc1,R).
reverse2_pretty(L,R):-reverse2(L,[],R).

%Minim
%recursivitate inapoi
min1([H|T],M):- min1(T,M),M<H,!.
min1([H|_],H).

%recursivitate inainte

min2([], Mp, M) :- M=Mp.
min2([H|T], Mp, M) :- H<Mp, !, min2(T, H, M).
min2([H|T], Mp, M) :- min2(T, Mp, M).
min2_pretty([H|T], M) :- min2(T, H, M). 

%Union
union([],L,L).
union([H|T],L2,R):-member(H,L2),!,union(T,L2,R).
union([H|T], L2, [H|R]) :- union(T, L2, R).

%Lab4 ex1
inters([],_,[]).
inters([H|T],L2,[H|R]):- member(H,L2),!,inters(T,L2,R).
inters([H|T], L2, R) :- inters(T, L2, R).

%Lab4 ex2
diff([],_,[]).
diff([H|T],L2,[H|R]):- \+ member(H,L2),!,diff(T,L2,R).
diff([H|T], L2, R) :- diff(T, L2, R).

%Lab4 ex3
delete_all(X, [X|T], R) :- delete_all(X, T, R). 
delete_all(X, [H|T], [H|R]) :- delete_all(X, T, R).
delete_all(_, [], []).

%Delete min
del_min(L,R):- min1(L,R1),delete_all(R1,L,R),!.

max1([H|T],M):- max1(T,M),M>H,!.
max1([H|_],H).

%Delete max
del_max(L,R):- max1(L,R1),delete_all(R1,L,R),!.


%Lab4 ex4

reverse_k(T, 0, R) :- reverse(T, R).
reverse_k([H|T], K, [H|R]) :- K1 is K-1,reverse_k(T, K1, R).

%Lab4 ex5

rle_encode([H|T], R) :- rle_encode(T, H, 1, R).
rle_encode([H|T], F, K, R) :- H= F, !, K1 is K + 1, rle_encode(T, H, K1, R).
rle_encode([H|T], F, K, [[F,K]|R]) :-  rle_encode(T, H, 1, R).
rle_encode([], F, K, [[F,K]|[]]) .


rle_encode1([H1,H1|T],C,R):- C1 is C+1 ,rle_encode1([H1|T],C1,R).
rle_encode1([H1,H2|T],C,R):- R=[[H1,C]|RT],rle_encode1([H2|T],1,RT).
rle_encode1([H],C,[[H,C]]).

%Lab4 ex6
rotate_right(L, 0, L).
rotate_right(L, K, R) :-  append(X, [Last], L), K1 is K - 1, rotate_right([Last|X], K1, R), !.



%Lab4 ex7
select_n([H|T],C,K,R):- C<K,C1 is C+1,select_n(T,C1,K,R).
select_n([H|T],K,K,H).

rnd_select(L,0,[]).
rnd_select(L,K,[R2|R]):- K>0 ,K1 is K-1 , length1(L,L1) , P is random(L1), select_n(L,0,P,R2),rnd_select(L,K1,R).