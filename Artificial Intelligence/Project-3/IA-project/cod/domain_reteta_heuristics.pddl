(define (domain reteta)
(:requirements :strips :action-costs)
(:types ingredient1 ingredient2 ingredient3 ingredient4 final cuptor amestec) 
(:predicates 
    (nu_are_i1 ?i1 - ingredient1)
    (nu_are_i2 ?i2 - ingredient2)
    (nu_are_i3 ?i3 - ingredient3)
    (nu_are_i4 ?i4 - ingredient4)
    (nu_preparat_final ?fin - final)
    (neincalzit ?c - cuptor)
    (nu_are_amestec ?a - amestec)
    (nu_in_cuptor ?c - cuptor ?a - amestec)
    (nu_gata ?fin - final)
)

(:functions
   (calitate-i1 ?i1 - ingredient1) - number
   (calitate-i2 ?i2 - ingredient2) - number
   (calitate-i3 ?i3 - ingredient3) - number
   (calitate-i4 ?i4 - ingredient4) - number
   (total-cost) - number
)


(:action cumparare-ingredient1
    :parameters (?i1 - ingredient1) 
    :precondition (and (nu_are_i1 ?i1)) ;nu are i1
    :effect (and (not(nu_are_i1 ?i1)) ;are i1
            (increase (total-cost) (calitate-i1 ?i1))
            )
)
(:action cumparare-ingredient2
    :parameters (?i2 - ingredient2) 
    :precondition (and (nu_are_i2 ?i2)) ;nu are i2
    :effect (and (not(nu_are_i2 ?i2)) ;are i2
            (increase (total-cost) (calitate-i2 ?i2))
            )
)
(:action cumparare-ingredient3
    :parameters (?i3 - ingredient3) 
    :precondition (and (nu_are_i3 ?i3)) ;nu are i3
    :effect (and (not(nu_are_i3 ?i3)) ;are i3
                 (increase (total-cost) (calitate-i3 ?i3))
            )
)
(:action cumparare-ingredient4
    :parameters (?i4 - ingredient4) 
    :precondition (and (nu_are_i4 ?i4)) ;nu are i4
    :effect (and (not(nu_are_i4 ?i4)) ;are i4
	         (increase (total-cost) (calitate-i4 ?i4))
	    )
)


(:action start_reteta
    :parameters (?i1 - ingredient1 ?i2 - ingredient2 ?i4 - ingredient4
                 ?i3 - ingredient3 ?fin - final)
    :precondition (and (not (nu_are_i1 ?i1)) ;are i1
                       (not (nu_are_i2 ?i2)) ;are i2
                       (not (nu_are_i4 ?i4)) ;are i4
                       (not (nu_are_i3 ?i3)) ;are i3
                       (nu_preparat_final ?fin)) ;nu are produsul final
            
    :effect(and (not (nu_preparat_final ?fin)) ;incepe prepararea produsului final
                )       
)

(:action amestec_ingrediente
    :parameters (?i1 - ingredient1 ?i2 - ingredient2 ?i4 - ingredient4
                 ?i3 - ingredient3 ?a - amestec ?fin - final) 
    :precondition (and (not (nu_are_i1 ?i1)) ;are i1
                       (not (nu_are_i2 ?i2)) ;are i2
                       (not (nu_are_i3 ?i3)) ;are i3
                       (not (nu_are_i4 ?i4)) ;are i4
                       (not (nu_preparat_final ?fin)) ;produsul final se prepara
                       (nu_are_amestec ?a) ;nu are amestec
                  ) 
    :effect(and (nu_are_i1 ?i1) ;s-a consumat i1
                (nu_are_i2 ?i2) ;s-a consumat i2
                (nu_are_i3 ?i3) ;s-a consumat i3
                (nu_are_i4 ?i4) ;s-a consumat i4        
                (not (nu_are_amestec ?a)) ;are amestec                    
           )
)           
           
(:action incalzire_cuptor
    :parameters (?c - cuptor ?a - amestec ?fin - final)  
    :precondition (and (neincalzit ?c) ;cuptor neincalzit
                       (not (nu_are_amestec ?a)) ;are amestec  
                       (not (nu_preparat_final ?fin)) ;produsul final se prepara       
                       (nu_in_cuptor ?c ?a) ;amestecul nu e in cuptor
                  )       
    :effect
    	(and
    		(not (neincalzit ?c)) ;cuptor incalzit
    		(nu_are_amestec ?a) ;amestecul se consuma 
    		(nu_preparat_final ?fin) ;produsul nu se mai prepara
    		(not (nu_in_cuptor ?c ?a)) ;amestecul e in cuptor 
    	)
    

)

(:action gata_final
	:parameters (?c - cuptor ?a - amestec ?p - final)
	:precondition 
	   (and
		(not (nu_in_cuptor ?c ?a)) ;amestecul e in cuptor
		(not (neincalzit ?c)) ;cuptor incalzit
	   )
	:effect 
	   (and
	   	(neincalzit ?c) ;cuptorul e stins
	        (nu_in_cuptor ?c ?a) ;amestecul nu mai e in cuptor
	        (not (nu_gata ?p)) ;gata produsul final   )   	
))
