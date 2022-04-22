create or replace view vnotestud as
select s.nr_matr, concat(s.nume,' ', s.prenume) as numele, e.titlu_mat, e.fel_examen, n.nota
from student s join nota n on s.nr_matr = n.nr_matr join examen e on n.code = e.code;
