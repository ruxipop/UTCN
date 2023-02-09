
(defrule AGENT::initCycle-left-turn
    (declare (salience 89))
    (timp (valoare ?)) ;make sure it fires each cycle
=>
    (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>initCycle-left-turn prohibited by default " crlf))
    (assert (ag_bel (bel_type moment)  (bel_timeslice 0) (bel_pname left-turn-maneuver) (bel_pval allowed))) ;by default, we assume overtaking NOT valid
    ;(facts AGENT)
)

;------------------------Scenariu 1-------------------
(defrule AGENT::detectie-trecere-pietoni-dreapta
(timp (valoare ?t))
(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj crossing2) (percept_pname direction) (percept_pval right))
(ag_percept (percept_pobj crossing2) (percept_pname state) (percept_pval busy))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-trecere-pietoni-dreapta " crlf))
    (assert (ag_bel (bel_type moment) (bel_timeslice 0)  (bel_pname trecere_pietoni2) (bel_pval yes)))
    ;(facts AGENT)
)

(defrule AGENT::detectie-trecere-pietoni-stanga
(timp (valoare ?t))
(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj crossing1) (percept_pname direction) (percept_pval left))
(ag_percept (percept_pobj crossing1) (percept_pname state) (percept_pval busy))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-trecere-pietoni-stanga " crlf))
    (assert (ag_bel (bel_type moment) (bel_timeslice 0)  (bel_pname trecere_pietoni1) (bel_pval yes)))
  ;  (facts AGENT)
)

(defrule AGENT::scenariu1
(declare (salience -10))
(timp (valoare ?t))
?f<-(ag_bel (bel_type moment)  (bel_timeslice 0) (bel_pname left-turn-maneuver) (bel_pval allowed))
(ag_bel (bel_type moment) (bel_pname trecere_pietoni1) (bel_pval yes))
(not  (ag_bel (bel_type moment) (bel_pname trecere_pietoni2) (bel_pval yes)))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval left | ahead ))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>scenariu-1 " crlf))
  (retract ?f)
 (assert (ag_bel (bel_type moment)  (bel_timeslice 0) (bel_pname left-turn-maneuver) (bel_pval prohibited))) 
 
   ; (facts AGENT)

)

;
;---------Delete instantaneous beliefs, i.e, those which are not fluents
;
(defrule AGENT::hk-eliminate-momentan-current-bel
    (declare (salience -25))
    (timp (valoare ?)) ;make sure it fires each cycle
 ;   ?fmcb <- (ag_bel (bel_type moment) (bel_timeslice 0) (bel_pobj ?o) (bel_pname ?p) (bel_pval ?v))
=>(printout t "Ce")
    (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>hk-eliminate-momentan-current-bel " ?o " " ?p " " ?v crlf))
    (retract ?fmcb)
)


