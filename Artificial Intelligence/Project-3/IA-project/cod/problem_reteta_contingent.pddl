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
        
        
        (unknown (stricat_i1 ou1))
        (unknown (stricat_i1 ou2))
        (unknown (stricat_i1 ou3))
        (unknown (stricat_i1 ou4))
        (oneof (stricat_i1 ou1) (stricat_i1 ou2)(stricat_i1 ou3) (stricat_i1 ou4))
        (unknown (stricat_i2 mar1))
        (unknown (stricat_i2 mar2))
        (unknown (stricat_i2 mar3))
        (oneof (stricat_i2 mar1) (stricat_i2 mar2)(stricat_i2 mar3))
        (unknown (stricat_i3 lapte1))
        (unknown (stricat_i3 lapte2))
        (oneof (stricat_i3 lapte1) (stricat_i3 lapte2))
     
      
)

(:goal 
   (and 
   (not (nu_gata prajitura))
   ;se consuma ingredientele
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
  
   )
   
)
)

