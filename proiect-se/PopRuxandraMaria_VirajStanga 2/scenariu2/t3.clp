(ag_percept (percept_pobj ev2) (percept_pname isa) (percept_pval event))

(ag_percept (percept_pobj lights1) (percept_pname isa) (percept_pval  traffic_lights))
(ag_percept (percept_pobj lights1) (percept_pname partof) (percept_pval ev2))
(ag_percept (percept_pobj lights1) (percept_pname direction) (percept_pval ahead))
(ag_percept (percept_pobj lights1) (percept_pname color) (percept_pval green))
(ag_percept (percept_pobj lights1) (percept_pname proximity) (percept_pval 0))

(ag_percept (percept_pobj road1) (percept_pname isa) (percept_pval road))
(ag_percept (percept_pobj road1) (percept_pname partof) (percept_pval ev2))
(ag_percept (percept_pobj road1) (percept_pname direction) (percept_pval left))

(ag_percept (percept_pobj lane1) (percept_pname isa) (percept_pval line))
(ag_percept (percept_pobj lane1) (percept_pname partof) (percept_pval road1))
(ag_percept (percept_pobj lane1) (percept_pname width) (percept_pval 3))

(ag_percept (percept_pobj intersection1) (percept_pname isa) (percept_pval intersection))
(ag_percept (percept_pobj intersection1) (percept_pname partof) (percept_pval ev2))
(ag_percept (percept_pobj intersection1) (percept_pname direction) (percept_pval ahead))
(ag_percept (percept_pobj intersection1) (percept_pname heigth) (percept_pval 6))


(ag_percept (percept_pobj car1) (percept_pname isa) (percept_pval  car))
(ag_percept (percept_pobj car1) (percept_pname partof) (percept_pval  ev2))
(ag_percept (percept_pobj car1) (percept_pname on) (percept_pval  intersection))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval  left))
(ag_percept (percept_pobj car1) (percept_pname heigth) (percept_pval 4))
(ag_percept (percept_pobj car1) (percept_pname distantion_from_lights1) (percept_pval 1 ))