;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;  PERCEPT-MANAGER
;;
;;  Current percepts manipulation
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;
;-------Delete prior percepts ------------
;
(defrule PERCEPT-MANAGER::hk-ag_percepts
    (tic) ; to avoid deleting newly added percepts
    ?fp <- (ag_percept (percept_pname ?pn) (percept_pval ?pv))
=> 
    (if (eq ?*sim-in-debug* TRUE) then (printout t "    <D>hk-ag_percepts retract " ?pn " " ?pv crlf))
    (retract ?fp)
)

;
;-------Trecere la ciclul de timp urmator si adaugarea la WM a perceptii din acest ciclu------------
;
(defrule PERCEPT-MANAGER::advance-time-percepts
    (declare (salience -95)) ; executata dupa stergerea perceptiilor anterioare
    ?tc <- (tic) ; pentru a nu intra in bucla infinita
    ?tp <- (timp (valoare ?t))
=>
    (if (eq ?*sim-in-debug* TRUE) then (printout t "<D>PERCEPT-MANAGER: advance-time-percepts (+ stergere tic)" crlf))
    (bind ?nt (+ 1 ?t))
    (printout t "   PERCEPT-MANAGER: timp = " ?nt crlf)
    (retract ?tp)
    (assert (timp (valoare ?nt)))
    (retract ?tc) ; pentru a nu intra in bucla infinita
    (load-facts (sym-cat ?*perceptsDir* "t" ?nt ".clp"))
;    (printout t "   DRIVER-AGENT: facts: " crlf)
;    (facts AGENT)
)
