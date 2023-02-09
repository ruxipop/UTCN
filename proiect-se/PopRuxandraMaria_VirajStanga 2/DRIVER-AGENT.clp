(defrule AGENT::initCycle-left-turn
    (declare (salience 89))
    (timp (valoare ?)) ;make sure it fires each cycle
=>
    (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>initCycle-left-turn allowed by default " crlf))
    (assert (ag_bel (bel_type moment)  (bel_pname left-turn-maneuver) (bel_pval allowed))) ;by default, we assume left turn valid
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
    (assert (ag_bel (bel_type moment)   (bel_pname trecere_pietoni2) (bel_pval yes)))
)

(defrule AGENT::detectie-trecere-pietoni-stanga
(timp (valoare ?t))
(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj crossing1) (percept_pname direction) (percept_pval left))
(ag_percept (percept_pobj crossing1) (percept_pname state) (percept_pval busy))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-trecere-pietoni-stanga " crlf))
    (assert (ag_bel (bel_type moment)  (bel_pname trecere_pietoni1) (bel_pval yes)))
)

(defrule AGENT::invalidare-scenariu1
(declare (salience -10))
(timp (valoare ?t))
(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))
?f<-(ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval allowed))
(ag_bel (bel_type moment) (bel_pname trecere_pietoni1) (bel_pval yes))
(not  (ag_bel (bel_type moment) (bel_pname trecere_pietoni2) (bel_pval yes)))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval left | ahead ))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>invalidare-scenariu-1 " crlf))
  (retract ?f)
 (assert (ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval prohibited))) 
)



;------------------------Scenariu 2-------------------


(defrule AGENT::semafor-verde
(declare (salience 10))
(timp (valoare ?t))
?f<- (ag_bel (bel_type fluent)  (bel_pname semafor-rosu) (bel_pval yes))
(ag_percept (percept_pobj ev2 | ev3) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj lights1) (percept_pname color) (percept_pval green))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-semafor-verde" crlf))
 (retract ?f)
)

(defrule AGENT::semafor-rosu
(declare (salience 10))
(timp (valoare ?t))
(ag_percept (percept_pobj ev2 | ev3) (percept_pname isa) (percept_pval event))
?fec<-(ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval allowed))
(ag_percept (percept_pobj lights1) (percept_pname color) (percept_pval red))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-semafor-rosu" crlf))
  (assert (ag_bel (bel_type fluent)  (bel_pname semafor-rosu) (bel_pval yes)))
 (retract ?fec)
  (assert (ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval prohibited))) 
)

(defrule AGENT::detect-car-in-parking
(timp (valoare ?t))
(not(ag_bel (bel_type fluent)  (bel_pname semafor-rosu) (bel_pval yes)))
(ag_percept (percept_pobj lights1) (percept_pname proximity) (percept_pval ?proximity_lights))
(ag_percept (percept_pobj parking1) (percept_pname proximity) (percept_pval ?proximity_parking ))
(ag_percept (percept_pobj car1) (percept_pname on) (percept_pval  parking1))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval  back))
(test (> ?proximity_parking ?proximity_lights))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-masina-in-parcare" crlf))
  (assert (ag_bel (bel_type moment)  (bel_pname detectie-parcare) (bel_pval yes)))
)


(defrule AGENT::detect-car-in-intersection
(timp (valoare ?t))
(not(ag_bel (bel_type fluent)  (bel_pname semafor-rosu) (bel_pval yes)))
(ag_percept (percept_pobj road1) (percept_pname direction) (percept_pval left))
(ag_percept (percept_pobj lane1) (percept_pname partof) (percept_pval road1))
(ag_percept (percept_pobj lane1) (percept_pname width) (percept_pval ?width_line))
(ag_percept (percept_pobj intersection1) (percept_pname direction) (percept_pval ahead))
(ag_percept (percept_pobj intersection1) (percept_pname heigth) (percept_pval ?heigth_intersection))

(ag_percept (percept_pobj car1) (percept_pname on) (percept_pval  intersection))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval  left))
(ag_percept (percept_pobj car1) (percept_pname heigth) (percept_pval ?heigth_car))
(ag_percept (percept_pobj car1) (percept_pname distantion_from_lights1) (percept_pval ?distance_from_lights ))
 (or 
         (test ( < ( -  ?heigth_intersection ?heigth_car) ?width_line ))
         (test ( > ?distance_from_lights 1)))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-masina-in-intersectie" crlf))
  (assert (ag_bel (bel_type moment)  (bel_pname detectie-intersectie) (bel_pval yes)))
)


(defrule AGENT::invalidare-scenariu2
(declare (salience -10))
(timp (valoare ?t))
?f<-(ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval allowed))
(or
(ag_bel (bel_type moment)  (bel_pname detectie-parcare) (bel_pval yes))
(ag_bel (bel_type moment)  (bel_pname detectie-intersectie) (bel_pval yes))
)
=>
 (retract ?f)
  (assert (ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval prohibited))) 
)



;------------------------Scenariu 3-------------------


(defrule AGENT::marcaj-linie
(timp (valoare ?t))
(ag_percept (percept_pobj ev3) (percept_pname isa) (percept_pval event))
(ag_percept (percept_pobj line1) (percept_pname status) (percept_pval on))
(ag_percept (percept_pobj mark1) (percept_pname type) (percept_pval ?value))
=>
 (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>detectie-marcaj-linie " crlf))
 (if (eq ?value  continuous)       then   (assert (ag_bel (bel_type moment)   (bel_pname linie-continua) (bel_pval yes))))
  (if (eq ?value  discontinuous)  then  (assert (ag_bel (bel_type moment)   (bel_pname linie-discontinua) (bel_pval yes))))
)


(defrule AGENT::invalidare-scenariu3
(declare (salience -10))
(timp (valoare ?t))
?f<-(ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval allowed))
(or
(ag_bel (bel_type moment)   (bel_pname linie-continua)  (bel_pval yes))
(and 
(ag_bel (bel_type moment)   (bel_pname linie-discontinua) (bel_pval yes))
(ag_percept (percept_pobj line2) (percept_pname status) (percept_pval busy))))
=>
 (retract ?f)
  (assert (ag_bel (bel_type moment) (bel_pname left-turn-maneuver) (bel_pval prohibited))) 
)