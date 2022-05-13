


neighb_to_edge:-retract(neighbor(Node,List)),!,
                process(Node,List),
                neighb_to_edge.
neighb_to_edge. 

process(Node, [H|T]):- assertz(edge(Node, H)), process(Node, T). 
process(Node, []):- assertz(node(Node)).



:-dynamic edge/2.
path(X,Y,Path):-path(X,Y,[X],Path).
path(Y,Y,PPath, PPath).
path(X,Y,PPath, FPath):- edge(X,Z),
     not(member(Z, PPath)),
path(Z, Y, [Z|PPath], FPath).



:-dynamic edge/2.
restricted_path(X,Y,LR,P):- path(X,Y,P),reverse(P,PR),check_restrictions(LR, PR).

check_restrictions([],_):- !.
check_restrictions([H|T], [H|R]):- !, check_restrictions(T,R). 
check_restrictions(T, [_|L]):-check_restrictions(T,L).



optimal_path(X,Y,Path):- asserta(sol_part([],100)), path(X,Y,[X],Path,1).
optimal_path(_,_,Path):- retract(sol_part(Path,_)).
path(Y,Y,Path,Path,LPath):-retract(sol_part(_,_)),!,asserta(sol_part(Path,LPath)),fail.
path(X,Y,PPath,FPath,LPath):- edge(X,Z),not(member(Z,PPath)),LPath1 is LPath+1,sol_part(_,Lopt),LPath1<Lopt,path(Z,Y,[Z|PPath],FPath,LPath1).




%ex1
:-dynamic edge/2.


edge_to_neighb:-retract(edge(Node1,Node2)),!,process_2(Node1,Node2),edge_to_neighb.
edge_to_neighb.

process_2(X,Y):-retract(neighbor(X,Z)),!,proc(X,Y,Z).
process_2(X,Y):-assertz(neighbor(X,[Y])).

proc(X,Y,Z):- \+(member(Y,Z)),!,
				assertz(neighbor(X,[Y|Z])).
proc(X,_,Z):-assertz(neighbor(X,Z)).


%ex2

:-dynamic edge/2.
node(a). node(b). %etc
edge(a,b). edge(b,a). 
edge(b,c). edge(c,b).
edge(c,d). edge(d,c).
edge(d,a). edge(a,d).
edge(b,d). edge(d,b).%etc

smember(Y,_,Y,1):-!,fail.
smember(X,L,_,_):-member(X,L).

hamilton(NN, X, Path):- NN1 is NN-1, hamilton_path(NN,X, X,[X],Path).
hamilton_path(0,X, X,Path,Path):-!. 
hamilton_path(NN,Z, X,L,Path):-
                              edge(Z,W),
                              not(smember(W, L,X,NN)),
                              NN1 is NN-1,
                              hamilton_path(NN1,W,X,[W|L],Path).


%ex3
/*
:-dynamic edge/3.
node(a). node(b). %etc
edge(a,b,1). edge(b,a,1). 
edge(b,c,2). edge(c,b,2). 
edge(c,a,5). edge(a,c,5).%etc

optimal_path_cost(X,Y,Path):- asserta(sol_part([],100)), path_cost(X,Y,[X],Path,1).
optimal_path_cost(_,_,Path):- retract(sol_part(Path,_)).
path_cost(Y,Y,Path,Path,LPath):-retract(sol_part(_,_)),!,asserta(sol_part(Path,LPath)),fail.
path_cost(X,Y,PPath,FPath,LPath):- edge(X,Z,C),not(member(Z,PPath)),LPath1 is LPath+C,sol_part(_,Lopt),LPath1<Lopt,path_cost(Z,Y,[Z|PPath],FPath,LPath1).
*/

%ex4
/*:-dynamic edge/2.
node(a). node(b). %etc
edge(a,b). edge(b,a). 
edge(b,c). edge(c,b).
edge(c,d). edge(d,c).
edge(d,a). edge(a,d).
edge(b,d). edge(d,b).%etc*/

/*Cauta daca exista muchie intre nodul dat de noi, dupa care afla path-ul de la nodul Z la X.
Lungimea path-ului trebuie sa fie mai mare decat 2,pentru a fi un ciclu, pentru ca daca e mai mica decat 2 va fi doar o muchie nedirectionala,
dupa care concatenam path-ul cu nodul dat de noi*/

cycle(X, R) :- edge(X,Z),
               path(Z,X,Path),
               length(Path,PathL),
               PathL>2,append(Path,[X],R).