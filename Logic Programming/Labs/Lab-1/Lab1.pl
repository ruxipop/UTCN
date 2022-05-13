% Predicatul woman/1
woman(ana).
woman(sara).
woman(ema).
woman(maria).
woman(dorina).
woman(irina).
woman(carmen).

% Predicatul man/1
man(marius).
man(sergiu).
man(mihai).
man(george).
man(andrei).
man(alex).

% Predicatul parent/2
parent(maria, ana). % maria este părintele anei
parent(george, ana). % george este părintele anei
parent(maria, andrei).
parent(george, andrei). % ...
parent(marius,maria).
parent(dorina,maria).
parent(mihai,george).
parent(irina,george).
parent(irina,carmen).
parent(mihai,carmen).
parent(carmen,sara).
parent(alex,sara).
parent(carmen,ema).
parent(alex,ema).


% Predicatul mother/2
mother(X,Y) :- woman(X), parent(X,Y).

% Predicatul father/2
father(X,Y) :- man(X), parent(X,Y).

% Predicatul sibling/2
% X și Y sunt frați/surori dacă au același parinte și X diferit de Y 
sibling(X,Y) :- parent(Z,X), parent(Z,Y), X\=Y.


% Predicatul sister/2
% X este sora lui Y dacă X este femeie și X și Y sunt frați/surori
sister(X,Y) :- sibling(X,Y), woman(X).

% Predicatul aunt/2
% X este mătușa lui Y daca este sora lui Z și Z este părintele lui Y
aunt(X,Y) :- sister(X,Z), parent(Z,Y).


% Predicatul bother/2
brother(X,Y) :- sibling(X,Y), man(X).

% Predicatul uncle/2
uncle(X,Y) :- brother(X,Z), parent(Z,Y).

% Predicatul grandmother/2
grandmother(X,Y) :- parent(X,Z),parent(Z,Y), woman(X).

% Predicatul grandfather/2
grandfather(X,Y) :- parent(X,Z),parent(Z,Y), man(X).

% Predicatul ancestor/2
ancestor(X,Y) :- parent(X,Y).
ancestor(X,Y) :- parent(X,Z), ancestor(Z,Y).


