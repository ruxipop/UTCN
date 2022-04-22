(define (problem reteta-pizza) 
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
        
        
        (unknown (stricat_i1 branza1))
        (unknown (stricat_i1 branza2))
        (oneof (stricat_i1 branza1) (stricat_i1 branza2))
        (unknown (stricat_i2 masline1))
	(unknown (stricat_i2 masline2))	
	(unknown (stricat_i2 masline3))
	(unknown (stricat_i2 masline4))
        (oneof (stricat_i2 masline1) (stricat_i2 masline2)(stricat_i2 masline3)(stricat_i2 masline4))
      
      
)

(:goal 
   (and 
   (not (nu_gata pizza))
   ;se consuma ingredientele
   (nu_are_i1 branza1)
   (nu_are_i1 branza2)
   (nu_are_i2 masline1)
   (nu_are_i2 masline2)
   (nu_are_i2 masline3)
   (nu_are_i2 masline4)
   (nu_are_i3 salam)
   (nu_are_i4 ciuperci)
  
   )
   
)
)

