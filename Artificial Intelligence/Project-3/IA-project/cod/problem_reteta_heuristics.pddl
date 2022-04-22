(define (problem reteta-prajitura-mere) 
(:domain reteta)
(:objects 
    ou1 ou2 ou3 ou4 - ingredient1
    mar1 mar2 mar3 - ingredient2
    lapte1 lapte2 - ingredient3
    faina - ingredient4
    prajitura - final
    cuptor - cuptor
    aluat - amestec
)

(:init
	
	(nu_are_i1 ou1)
	(nu_are_i1 ou2)
	(nu_are_i1 ou3)
	(nu_are_i1 ou4)
	(nu_are_i2 mar1)
	(nu_are_i2 mar2)
	(nu_are_i2 mar3)
	(nu_are_i3 lapte1)
	(nu_are_i3 lapte2)
	(nu_are_i4 faina)
	(nu_preparat_final prajitura) 
	(neincalzit cuptor)
        (nu_are_amestec aluat)
        (nu_in_cuptor cuptor aluat)
        (nu_gata prajitura)
        
        
        (= (calitate-i1 ou1) 5) ;cel mai rau
        (= (calitate-i1 ou2) 4)
        (= (calitate-i1 ou3) 2) ;cel mai bun
        (= (calitate-i1 ou4) 4) 
        
        (= (calitate-i2 mar1) 10) ;cel mai rau
        (= (calitate-i2 mar2) 6)
        (= (calitate-i2 mar3) 4) ;cel mai bun
        
        
        (= (calitate-i3 lapte1) 3) 
        (= (calitate-i3 lapte2) 4) ;cel mai rau
        
        (= (calitate-i4 faina) 1) 
      
)

(:goal 
   (and 
   (not (nu_gata prajitura))
   )
)
  (:metric minimize (total-cost))
)

