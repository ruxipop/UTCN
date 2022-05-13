max1(A,B,R):- R is max(A,B).
%member
member_il(_,L):-var(L),!,fail.
member_il(X,[X|_]):-!.
member_il(X,[_|T]):-member_il(X,T).

%insert
insert_il(X,L):-var(L),!,L=[X|_].
insert_il(X,[X|_]):-!. 
insert_il(X,[_|T]):-insert_il(X,T).

%delete_list

delete_il(_, L, L):-var(L), !.
delete_il(X, [X|T], T):-!. 
delete_il(X, [H|T], [H|R]):-delete_il(X, T, R).


%search

search_it(_,T):-var(T),!,fail.
search_it(Key,t(Key,_,_)):-!.
search_it(Key,t(K,L,_)):-Key<K,!,search_it(Key,L).
search_it(Key,t(_,_,R)):-search_it(Key,R).

%insert_list

insert_it(Key,t(Key,_,_)):-!.
insert_it(Key,t(K,L,_)):-Key<K,!,insert_it(Key,L).
insert_it(Key,t(_,_,R)):-insert_it(Key,R).

%delete_tree

delete_it(_, T, T):-var(T),!.
delete_it(Key, t(Key, L,R), L):-var(R),!.
delete_it(Key, t(Key, L,R), R):-var(L),!.
delete_it(Key, t(Key, L,R), t(Pred,NL,R)):-!,get_pred(L,Pred,NL).
delete_it(Key, t(K,L,R),t(K,NL,R)):-Key<K,!,delete_it(Key,L,NL).
delete_it(Key, t(K,L,R),t(K,L,NR)):-delete_it(Key,R,NR).

%cauta_pred
get_pred(t(Pred, L, R), Pred, L):-var(R),!.
get_pred(t(Key, L, R), Pred, t(Key, L, NR)):-get_pred(R, Pred, NR).


%ex1_append_il

append_il([H|T],L2,[H|L2]):-var(T),!.
append_il([H|T],L2,[H|R]):-append_il(T,L2,R).


%ex2_reverse_il

reverse_il([H| T], Acc, [H| Acc]) :- var(T),!.
reverse_il([H| T], Acc, R) :-reverse_il(T, [H| Acc], R).

%ex3_conv_il

conv_il(L,[]):-var(L),!.
conv_il([H|T],[H|R]):-conv_il(T,R).

conv_to_in([H|T],[H|_]):-atomic(T),!.
conv_to_in([H|T],[H|R]):-conv_to_in(T,R).

%ex4_preorder

preorder_it(t(K,_,_),K):-var(K),!.
preorder_it(t(K,L,R), List):-preorder_it(L,LL), preorder_it(R, LR), append([K|LL], LR, List).


%ex5_height

height_it(t(K,_,_), 0):-var(K),!.
height_it(t(_, L, R), H):-height_it(L, H1),height_it(R, H2), max1(H1, H2, H3), H is H3+1.

%ex6_conv_it

conv_it(T,nil):-var(T),!.
conv_it(t(K,L,R),t(K,LL,LR)):-conv_it(L,LL),conv_it(R,LR).

conv_to_ar(t(T,nil,nil),t(T,_,_)) :-atomic(T),!.
conv_to_ar(t(K,L,R),t(K,LL,LR)):-conv_to_ar(L,LL),conv_to_ar(R,LR).

%ex7_flat_il

flat_il(T,T):-var(T),!.
flat_il([H|T],[H|R]):-atomic(H),!,flat_il(T,R).
flat_il([H|T],R):-flat_il(H,R1),flat_il(T,R2),append(R1,R2,R).

%ex8_diam_it

diam_it(t(K,_,_), 0):-var(K),!.
diam_it(t(_,L,R),D):- diam_it(L,D1),diam_it(R,D2),height_it(L,H1),height_it(R,H2), H is H1+H2+1 ,max1(D1,D2,M1),max1(M1,H,D).


%ex9_subl_il
matchList(L,_):- var(L),!.		
matchList([H|T1],[H|T2]) :-matchList(T1,T2).	

subl_il(L1,L2):- matchList(L1,L2),!.	
subl_il(L1,[_|T]):- subl_il(L1,T).		
subl_il(_,L):- var(L),!,false.

