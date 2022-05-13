%Varianta 1
cmmdc1(X,X,X).
cmmdc1(X,Y,Z):-X>Y ,Diff is X-Y ,cmmdc1(Diff,Y,Z).
cmmdc1(X,Y,Z):-X<Y,Diff is Y-X,cmmdc1(X,Diff,Z).

%Varianta 2
cmmdc2(X,0,X).
cmmdc2(X,Y,Z):- Rest is X mod Y ,cmmdc2(Y,Rest,Z).

%Factorial  backwards

factorial1(0,1).
factorial1(N,Y):-N>0, N2 is N - 1 ,  factorial1(N2,Y1) ,Y is N * Y1.

%Factorial  forward  in acumulator(in ultimul pas al recursivitati)

factorial2(0,Acc,F) :- F = Acc.
factorial2(N,Acc,F) :- N > 0, N1 is N - 1 ,Acc1 is Acc *N ,factorial2(N1,Acc1,F).
factorial2(N,F):- factorial2(N,1,F). % acc initializat cu 1

%CMMMC

cmmmc(X,Y,Z) :-  P is X * Y , cmmdc2(X,Y,C), Z is P div C.

%Power  backwards

power1(X,1,X).
power1(X,Y,Z) :- Y>=0 , Diff is Y - 1, power1(X,Diff,In) ,Z is X * In.


%Power  forward
power2(_,1,Acc,F) :- F = Acc.
power2(X,N,Acc,F) :- N>=0,Diff is N-1 , Acc1 is Acc * X,power2(X,Diff,Acc1,F).
power2(X,N,F) :- power2(X,N,X,F).

%Fibonnaci

fib(0,0).
fib(1,1).
fib(N,R) :-  N > 0,N1 is N-1, N2 is N-2, fib(N1,Y1) , fib(N2,Y2), R is Y1 + Y2.

%Fibonnaci with one call
fib2(N,R):- fib2(N,_,R).
fib2(1,0,1).
fib2(N,R1,R) :-  N1 is N-1, fib2(N1,R2,R1), R is R1 + R2.

%Triangle/3

triangle(A,B,C) :-  S1 is A + B ,S2 is A+C ,S3 is B+C , S1 > C ,S2 > B,S3>A,A>0,B>0,C>0.

%Solve/4

solve(A, B, C, X) :- Delta is B * B - 4 * A * C, Delta >= 0, X is ((0 - B) + sqrt(Delta)) / (2 * A). 
solve(A, B, C, X) :- Delta is B * B - 4 * A * C, Delta >= 0, X is ((0 - B) - sqrt(Delta)) / (2 * A).
	

