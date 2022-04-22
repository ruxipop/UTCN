(define (problem reteta-prajitura-pizza) 
(:domain reteta)
(:objects 
    branza1 branza2 - ingredient1
    masline1 masline2 masline3 masline4 - ingredient2
    salam - ingredient3
    ciuperci - ingredient4
    pizza - final
    cuptor - cuptor
    aluat - amestec
)

(:init

	(nu_are_i1 branza1)
	(nu_are_i1 branza2)
	(nu_are_i2 masline1)
	(nu_are_i2 masline2)
	(nu_are_i2 masline3)
	(nu_are_i2 masline4)
	(nu_are_i3 salam)
	(nu_are_i4 ciuperci)
	(nu_preparat_final pizza) 
	(neincalzit cuptor)
        (nu_are_amestec aluat)
        (nu_in_cuptor cuptor aluat)
        (nu_gata pizza)
        
        
        (= (calitate-i1 branza1) 2) ;cel mai bun
        (= (calitate-i1 branza2) 4) ;cel mai rau
        
        (= (calitate-i2 masline1) 1) ;cel mai bun
        (= (calitate-i2 masline2) 4)
        (= (calitate-i2 masline3) 1) ;cel mai bun
        (= (calitate-i2 masline4) 8) ;cel mai rau
        
        
        (= (calitate-i3 salam) 1) 
        (= (calitate-i4 ciuperci) 1) 
      
)

(:goal 
   (and 
   (not (nu_gata pizza))
   )
)
  (:metric minimize (total-cost))
)

