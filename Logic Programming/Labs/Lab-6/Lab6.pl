%predicatul_max
max1(X,Y,R):-R is max(X,Y).

%predicatul_min
min1([H|T],M):- min1(T,M),M<H,!.
min1([H|_],H).


%predicatul_depth
depth([],1):-!.
depth(H, 0):- atomic(H),!.
depth([H|T],R):-atomic(H),!,depth(T,R).
depth([H|T],R):-depth(H,R1),depth(T,R2),R3 is R1+1,max1(R3,R2,R).

%predicatul_flatten
flatten([],[]).
flatten([H|T],[H|R]):-atomic(H),!,flatten(T,R).
flatten([H|T],R):-flatten(H,R1),flatten(T,R2),append(R1,R2,R).


%predicatul_heads
heads([],[],_).
heads([H|T],[H|R],1):-atomic(H),!,heads(T,R,0).
heads([H|T],R,0):- atomic(H), !, heads(T,R,0).
heads([H|T],R,_):- heads(H,R1,1), heads(T,R2,0), append(R1,R2,R).
heads_pretty(L,R):- heads(L, R, 1).


%predicatul_member_v1
member1(H,[H|_]).
member1(X,[H|_]):-member1(X,H).
member1(X,[_|T]):-member1(X,T).

%predicatul_member_v2
member2(X,L):-flatten(L,L1),member(X,L1).

%predicatul_count_atomic
count_atomic([H|T],R):-atomic(H),!,count_atomic(T,R1),R is R1+1.
count_atomic([_|T],R):-count_atomic(T,R).
count_atomic([],0).

%predicatul_sum_atomic
sum_atomic([H|T],R):-atomic(H),!,sum_atomic(T,R1),R is R1+H.
sum_atomic([_|T],R):-sum_atomic(T,R).
sum_atomic([],0).

%predicatul_member_determinist_taiam_back
member_det(H,[H|_]):-!.
member_det(X,[H|_]):-member_det(X,H),!. 
member_det(X,[_|T]):-member_det(X,T).

%predicatul_lasts
lasts([],[]):-!.
lasts([H],[H]):-atomic(H),!.
lasts([H],R):-lasts(H,R),!.
lasts([H|T],R):-atomic(H),!,lasts(T,R).
lasts([H|T],R):-lasts(H,R1),lasts(T,R2),append(R1,R2,R).


%predicatul_replace
replace(_,_,[],[]).
replace(X,Y,[X|T],[Y|R]):-!,replace(X,Y,T,R).
replace(X,Y,[H|T],[H|R]):-atomic(H),!,replace(X,Y,T,R).
replace(X,Y,[H|T],[R1|R2]):-replace(X,Y,H,R1),replace(X,Y,T,R2).


%predicatul_sort_depth_cu_sortare_prin_insertie
ins_sort([H|T],R):-ins_sort(T,R1),insert_ord(H,R1,R).
ins_sort([],[]).

insert_ord(X,[H|T],[H|R]):-depth(X,X1),depth(H,H1), X1>H1,!,insert_ord(X,T,R).
insert_ord(X,T,[X|T]).


%predicatul_sort_depth_cu_sortare_rapida
quick_sort([H|T], R):- 
            partition(H, T, Sm, Lg),
            quick_sort(Sm, SmS), 
            quick_sort(Lg, LgS),
            append(SmS, [H|LgS], R). 
quick_sort([], []).
partition(H, [X|T], [X|Sm], Lg):-depth(X,X1),depth(H,H1), X1<H1, !, partition(H, T, Sm, Lg). 
partition(H, [X|T], Sm, [X|Lg]):-partition(H, T, Sm, Lg). 
partition(_, [], [], []).



%predicatul_sort_depth_cu_bubble_sort
bubble_sort(L,R):-one_pass(L,R1,F), nonvar(F), !, bubble_sort(R1,R).
bubble_sort(L,L).

one_pass([H1,H2|T], [H2|R], F):-depth(H1, H1D), depth(H2, H2D), H1D>H2D, !, F=1, one_pass([H1|T],R,F).
one_pass([H1|T], [H1|R], F):-one_pass(T, R, F).
one_pass([], [] ,_).


