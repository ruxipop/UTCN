;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;  Modulul MAIN - reguli
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defrule MAIN::succesiune "change current module in sequence"
  ?lista <- (secventa ?urm $?rest)
 =>
  (retract ?lista)
  (assert (secventa $?rest ?urm))
  (if (eq ?urm PERCEPT-MANAGER)
  then (assert (tic)) ; folosit in PERCEPT-MANAGER ca sa incrementeze timpul
       (if (eq ?*ag-tic-in-debug* TRUE) then (printout t "    <D> MAIN: adaugare tic" crlf)))
  (if (eq ?*main-in-debug* TRUE) then (printout t "    <D> MAIN: switch to " ?urm  crlf))
  ;(facts ?urm) 
  (focus ?urm)
)
