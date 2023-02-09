(ag_percept (percept_pobj ev2) (percept_pname isa) (percept_pval event))

(ag_percept (percept_pobj lights1) (percept_pname isa) (percept_pval  traffic_lights))
(ag_percept (percept_pobj lights1) (percept_pname partof) (percept_pval ev2))
(ag_percept (percept_pobj lights1) (percept_pname direction) (percept_pval ahead))
(ag_percept (percept_pobj lights1) (percept_pname color) (percept_pval green))
(ag_percept (percept_pobj lights1) (percept_pname proximity) (percept_pval 0))

(ag_percept (percept_pobj parking1) (percept_pname isa) (percept_pval parking))
(ag_percept (percept_pobj parking1) (percept_pname partof) (percept_pval ev2))
(ag_percept (percept_pobj parking1) (percept_pname direction) (percept_pval right))
(ag_percept (percept_pobj parking1) (percept_pname proximity) (percept_pval 1))

(ag_percept (percept_pobj car1) (percept_pname isa) (percept_pval  car))
(ag_percept (percept_pobj car1) (percept_pname partof) (percept_pval  ev2))
(ag_percept (percept_pobj car1) (percept_pname on) (percept_pval  parking1))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval  back))