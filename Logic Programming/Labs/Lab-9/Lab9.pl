tree1(t(6, t(4, t(2, nil, nil), t(5, nil, nil)), t(9, t(7, nil, nil), nil))).
add1(X,[H|T],[H|R]):-add1(X,T,R).
add1(X,[],[X]).

%insert_last
add2(X,LS,LE,RS,RE):-RS=LS,LE=[X|RE].

%append
append_dl(LS1,LE1,LS2,LE2,RS,RE):-RS=LS1,LE1=LS2,RE=LE2.

%inorder
inorder_dl(nil,L,L).
inorder_dl(t(K,L,R),LS,LE):-inorder_dl(L,LSL,LEL),inorder_dl(R,LSR,LER),LS=LSL,LEL=[K|LSR],LE=LER.

%partition
partition(H, [X|T], [X|Sm], Lg):-X<H, !, partition(H, T, Sm, Lg).
partition(H, [X|T], Sm, [X|Lg]):-partition(H, T, Sm, Lg).
partition(_, [], [], []).

%quicksort
quicksort_dl([H|T], S, E):-
          partition(H, T, Sm, Lg),
          quicksort_dl(Sm, S, [H|L]),
          quicksort_dl(Lg,L,E).
quicksort_dl([],L,L).

%fibonacci
:-dynamic memo_fib/2.

fib(N,F):- memo_fib(N,F), !. 
fib(N,F):- N>1,
           N1 is N-1,
           N2 is N-2,
           fib(N1,F1), 
           fib(N2,F2),
           F is F1+F2, 
           assertz(memo_fib(N,F)).

fib(0,1). 
fib(1,1).

%show

print_all:-memo_fib(N,F), 
                write(N),
                write(' - '), 
                write(F),
                nl,
                fail.
print_all.

%findall-predefinit

%permutari
all_perm(L,_):- perm(L,L1), 
                assertz(p(L1)),
                fail. 
all_perm(_,R):- collect_perms(R).
collect_perms([L1|R]):- retract(p(L1)), !, collect_perms(R). 
collect_perms([]).


%ex1
incToDiff(LS,LS,LS):- var(LS),!.         
incToDiff([H|T], LE,[H|T1]):- incToDiff(T,LE,T1).        

diffToInc(LS,_):-var(LS), !.  
diffToInc([H|T],[H|IL]):-diffToInc(T,IL). 

%ex2
comToDiff([],LE,LE):-!.
comToDiff([H|T],LE,[H|R]):-comToDiff(T,LE,R).

diffToCom(LS, []) :- var(LS), !.
diffToCom([H|LS], [H|R]) :- diffToCom(LS,  R).

%ex3
:- dynamic a/2.
all_decompositions(L, _) :- append(X, Y, L), assertz(a(X, Y)), fail.
all_decompositions(_,List):- collect_decompositions(List).
collect_decompositions([X, Y|R]):- retract(a(X, Y)), !, collect_decompositions(R).
collect_decompositions([]).

%ex4
flat_dl([],LS,LS).
flat_dl([H|T],[H|RS],RE):-atomic(H),!,flat_dl(T,RS,RE).
flat_dl([H|T],RS,RE):-flat_dl(H,RS1,RE1),flat_dl(T,RS2,RE2),RS=RS1,RE1=RS2,RE=RE2.


%ex5
collect(nil,L,L):-!.
collect(t(K,L,R),RS,RE):-0 is K mod 2,!,collect(L,RS,[K|LE]),collect(R,LE,RE).
collect(t(_,L,R),RS,RE):-collect(L,RS,LE),collect(R,LE,RE).

%ex6
collect2(nil,L,L,_,_):-!.
collect2(t(K,L,R),RS,RE,K1,K2):-K>K1,K<K2,!,collect2(L,RS,[K|LE],K1,K2),collect2(R,LE,RE,K1,K2).
collect2(t(_,L,R),RS,RE,K1,K2):-collect2(L,RS,LE,K1,K2),collect2(R,LE,RE,K1,K2).