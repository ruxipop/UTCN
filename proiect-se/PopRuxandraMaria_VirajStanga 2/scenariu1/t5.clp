(ag_percept (percept_pobj ev1) (percept_pname isa) (percept_pval event))

(ag_percept (percept_pobj crossing1) (percept_pname isa) (percept_pval ped_crossing))
(ag_percept (percept_pobj crossing1) (percept_pname partof) (percept_pval ev1))
(ag_percept (percept_pobj crossing1) (percept_pname direction) (percept_pval left))
(ag_percept (percept_pobj crossing1) (percept_pname state) (percept_pval busy))


(ag_percept (percept_pobj crossing2) (percept_pname isa) (percept_pval ped_crossing))
(ag_percept (percept_pobj crossing2) (percept_pname partof) (percept_pval ev1))
(ag_percept (percept_pobj crossing2) (percept_pname direction) (percept_pval right))
(ag_percept (percept_pobj crossing2) (percept_pname state) (percept_pval free))

(ag_percept (percept_pobj sign1) (percept_pname isa) (percept_pval road_sign))
(ag_percept (percept_pobj sign1) (percept_pname partof) (percept_pval ev1))
(ag_percept (percept_pobj sign1) (percept_pname type) (percept_pval priority))
(ag_percept (percept_pobj sign1) (percept_pname direction) (percept_pval ahead))

(ag_percept (percept_pobj road1) (percept_pname isa) (percept_pval road))
(ag_percept (percept_pobj road1) (percept_pname partof) (percept_pval ev1))
(ag_percept (percept_pobj road1) (percept_pname direction) (percept_pval ahead))

(ag_percept (percept_pobj car1) (percept_pname isa) (percept_pval car))
(ag_percept (percept_pobj car1) (percept_pname partof) (percept_pval ev1))
(ag_percept (percept_pobj car1) (percept_pname on) (percept_pval road1))
(ag_percept (percept_pobj car1) (percept_pname intention) (percept_pval left))