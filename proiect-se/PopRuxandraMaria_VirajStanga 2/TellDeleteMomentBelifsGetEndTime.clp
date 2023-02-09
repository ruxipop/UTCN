;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; DRIVER AGENT housekeeping
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;
;--------Print decision-----------------------------------
;
(defrule AGENT::tell
    (declare (salience -50))
    (timp (valoare ?)) ;make sure it fires each cycle
    (ASK ?bprop)
     ;?fcvd <- (ag_bel (bel_type moment) (bel_timeslice 0) (bel_pname ?bprop) (bel_pval ?bval))
    ?fcvd <- (ag_bel (bel_type moment) (bel_pname ?bprop) (bel_pval ?bval))
=>
    (printout t "         AGENT " ?bprop " " ?bval crlf)
    (retract ?fcvd)
)



;
;---------Delete instantaneous beliefs, i.e, those which are not fluents
;
(defrule AGENT::hk-eliminate-momentan-current-bel
    (declare (salience -85))
    (timp (valoare ?)) ;make sure it fires each cycle
    ?fmcb <- (ag_bel (bel_type moment) (bel_pobj ?o) (bel_pname ?p) (bel_pval ?v))
=>
    (if (eq ?*ag-in-debug* TRUE) then (printout t "    <D>hk-eliminate-momentan-current-bel " ?o " " ?p " " ?v crlf))
    (retract ?fmcb)
)


;
;---------measure running time 
;
(defrule AGENT::time_end
  (declare (salience -92))
  ?fsta <- (tstart ?time_start)
=>

  (bind ?time_end (time))
  (bind ?ex_time (- ?time_end ?time_start))
  (if (eq ?*ag-measure-time* TRUE) then (printout t "            <M> AGENT Decision time: " ?ex_time " sec." crlf))
  (retract ?fsta))
