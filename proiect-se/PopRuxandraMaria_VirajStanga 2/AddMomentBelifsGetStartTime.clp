;;(defrule AGENT::afisare_tact "print time"
;;    (declare (salience 99))
;;    (timp (valoare ?t))
;;=>
;;    (printout t "t=" ?t crlf)
;;)

(defrule AGENT::time_start "measure running time. Credit: Portik Annamaria & Stan Lavinia, CTI 2019"
  (declare (salience 96))
  (timp (valoare ?t))
=>
  (assert (tstart (time))))

(defrule AGENT::percepts_into_beliefs "copy each percept into a moment belief to separate pecepts and beliefs"
  (declare (salience 93))
  (timp (valoare ?t))
  (ag_percept (percept_pobj ?pobj) (percept_pname ?pname) (percept_pval ?pval))
=>
  (if (eq ?*ag-percepts-in-debug* TRUE) then (printout t ?pobj "  " ?pname "  " ?pval crlf))
  (assert (ag_bel (bel_type moment) (bel_pobj ?pobj) (bel_pname ?pname) (bel_pval ?pval)))
)