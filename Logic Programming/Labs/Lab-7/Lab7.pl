tree1(t(6, t(4, t(2, nil, nil), t(5, nil, nil)), t(9, t(7, nil, nil), nil))).
tree2(t(8, t(5, nil, t(7, nil, nil)), t(9, nil, t(11, nil, nil)))).
tree3(t(6, t(4, t(2, nil, nil, nil), nil, t(7, nil, nil, nil)), t(5, nil, nil, nil), t(9, t(3, nil, nil, nil), nil, nil))).
tree4(t(a,t(b,t(d,nil,nil),t(e,nil,nil)),t(c,nil,t(f,t(g,nil,nil),nil))) ).

%max_3
max1(A,B,R):- R is max(A,B).

%parcurgere_inorder

inorder(t(K,L,R), List):-inorder(L,LL), inorder(R,LR), append(LL, [K|LR], List).
inorder(nil, []).

%parcurgere_preorder

preorder(t(K,L,R), List):-preorder(L,LL), preorder(R, LR), append([K|LL], LR, List).
preorder(nil, []).

%parcurgere_postorder

postorder(t(K,L,R), List):-postorder(L,LL), postorder(R, LR), append(LL, LR,R1), append(R1, [K], List).
postorder(nil, []).


%pretty_print

pretty_print(nil, _). 
pretty_print(t(K,L,R), D):-D1 is D+1,pretty_print(L, D1), print_key(K, D), pretty_print(R, D1).

print_key(K, D):-D>0, !, D1 is D-1, write('\t'), print_key(K, D1). 
print_key(K, _):-write(K), write('\n').   % write('\n') echivalent cu nl


%search_key_in_tree

search_key(Key,t(Key,_,_)):-!.
search_key(Key,t(K,L,_)):-Key<K,!,search_key(Key,L).
search_key(Key,t(_,_,R)):-search_key(Key,R).

%insert_key_in_tree

insert_key(Key, nil, t(Key, nil, nil)). 
insert_key(Key, t(Key, L, R), t(Key, L, R)):-!.
insert_key(Key, t(K,L,R), t(K,NL,R)):- Key<K,!,insert_key(Key,L,NL). 
insert_key(Key, t(K,L,R), t(K,L,NR)):- insert_key(Key, R, NR).


%delete_key_in_tree

delete_key(Key, t(Key, L, nil), L):-!.
delete_key(Key, t(Key, nil, R), R):-!.
delete_key(Key, t(Key, L, R), t(Pred,NL,R)):-!,get_pred(L,Pred,NL). 
delete_key(Key, t(K,L,R), t(K,NL,R)):-Key<K,!,delete_key(Key,L,NL). 
delete_key(Key, t(K,L,R), t(K,L,NR)):-delete_key(Key,R,NR).


%search_pred

get_pred(t(Pred, L, nil), Pred, L):-!.
get_pred(t(Key, L, R), Pred, t(Key, L, NR)):-get_pred(R, Pred, NR).


%height_in_tree

height(nil, 0).
height(t(_, L, R), H):-height(L, H1),
height(R, H2), max1(H1, H2, H3), H is H3+1.

%ex1-1.1

inorder_ternar(t(K,L,M,R), List):-inorder_ternar(L,LL), inorder_ternar(M,LM) ,inorder_ternar(R,LR), append(LL, [K|LM], L1),append(L1,LR,List).
inorder_ternar(nil, []).

%ex1-1.2

preorder_ternar(t(K,L,M,R), List):-preorder_ternar(L,LL), preorder_ternar(R, LR),preorder_ternar(M,LM),append([K|LL], LM, L1),append(L1,LR,List).
preorder_ternar(nil, []).

%ex1-1.3

postorder_ternar(t(K,L,M,R), List):-postorder_ternar(L,LL), postorder_ternar(M,LM),postorder_ternar(R, LR), append(LL,LM,L1),append(LR,[K],L2),append(L1,L2,List).
postorder_ternar(nil, []).

%ex2

height_ternar(nil, 0).
height_ternar(t(_, L,M, R), H):-height_ternar(L, H1),height_ternar(M,H3),height_ternar(R, H2), max1(H1, H2, R1),max1(R1,H3,R2) , H is R2+1.

%ex3

delete_key_succ(Key, t(Key, L, nil), L):-!.
delete_key_succ(Key, t(Key, nil, R), R):-!.
delete_key_succ(Key, t(Key, L, R), t(Succ,L,NR)):-!,get_succ(R,Succ,NR). 
delete_key_succ(Key, t(K,L,R), t(K,NL,R)):-Key<K,!,delete_key_succ(Key,L,NL). 
delete_key_succ(Key, t(K,L,R), t(K,L,NR)):-delete_key_succ(Key,R,NR).


get_succ(t(Succ,nil, R), Succ, R):-!.
get_succ(t(Key, L, R), Succ, t(Key, NL, R)):-get_succ(L, Succ, NL).


%ex4

collect_leaf(nil,[]).
collect_leaf(t(Key,nil,nil),[Key]):-!.
collect_leaf(t(_,L,R),List):-collect_leaf(L,R1),collect_leaf(R,R2),append(R1,R2,List).

%ex5

diam(nil,0).
diam(t(_,L,R),D):- diam(L,D1),diam(R,D2),height(L,H1),height(R,H2), H is H1+H2+1 ,max1(D1,D2,M1),max1(M1,H,D).

%ex6

same_depth(nil,0,[]).
same_depth(t(Key,_,_),0,[Key]):-!.
same_depth(t(_,L,R),D,List):-D1 is D-1 ,same_depth(L,D1,R1),same_depth(R,D1,R2),append(R1,R2,List).


%ex7

symmetric(nil).
symmetric(t(_,L,R)) :- symmetric_helper(L,R).

symmetric_helper(nil,nil).
symmetric_helper(t(_,L1,R1),t(_,L2,R2)) :- symmetric_helper(L1,R2), symmetric_helper(L2,R1).
