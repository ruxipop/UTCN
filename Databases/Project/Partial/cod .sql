
drop table autor;
drop table imprumut;
drop table carte;
drop table persoana;
   -------------------------------------------16.01-a----------------------------------------------------
CREATE TABLE Persoana(id_pers INT NOT NULL,
                      nume VARCHAR(50),
                      telefon VARCHAR(20),
                      adresa VARCHAR(50));

   -------------------------------------------16.01-b----------------------------------------------------
CREATE TABLE  Carte (id_carte INT NOT NULL,
                      titlu VARCHAR(50),
                      nr_pagini NUMBER(20),
                      nr_exemplare NUMBER(20),
                      gen VARCHAR(30),
                      rezumat VARCHAR(50));

   -------------------------------------------16.01-c----------------------------------------------------
CREATE TABLE Imprumut (id_carte INT NOT NULL,
                       id_imp INT NOT NULL,
                       datai DATE NOT NULL,
                       datar DATE,
                       nr_zile NUMBER(30));

   -------------------------------------------16.01-d----------------------------------------------------
CREATE TABLE Autor (id_carte INT NOT NULL,
                    id_aut INT NOT NULL);

   -------------------------------------------16.01-e----------------------------------------------------
ALTER TABLE Persoana ADD CONSTRAINT Pk_id_pers PRIMARY KEY (id_pers);
ALTER TABLE Carte ADD CONSTRAINT Pk_id_carte PRIMARY KEY (id_carte);
ALTER TABLE Imprumut ADD CONSTRAINT Pk_imprumut PRIMARY KEY (id_carte,id_imp,datai);
ALTER TABLE Autor ADD CONSTRAINT Pk_autor PRIMARY KEY (id_carte,id_aut);


 ALTER TABLE Autor  ADD 
	CONSTRAINT FK_aut_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
 ALTER TABLE Autor  ADD 
	CONSTRAINT FK_aut_id_autor FOREIGN KEY (id_aut) REFERENCES Persoana (id_pers);
 ALTER TABLE Imprumut ADD 
	CONSTRAINT FK_id_imp FOREIGN KEY (id_imp) REFERENCES Persoana(id_pers);
ALTER TABLE Imprumut ADD 
	CONSTRAINT FK_imp_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
	
	   -------------------------------------------16.01-f----------------------------------------------------
ALTER TABLE Carte
DROP COLUMN rezumat;

   -------------------------------------------16.02-a----------------------------------------------------

 ALTER TABLE Carte ADD 
	CONSTRAINT CK_nr_pg CHECK (nr_pagini>=10 and nr_pagini<=1000);
	
	   -------------------------------------------16.02-b----------------------------------------------------
ALTER TABLE Persoana ADD 
	CONSTRAINT CK_pers_adresa CHECK ( telefon !='+40%' OR (telefon='+40%' and adresa='Romania'));
	
	
		   -------------------------------------------16.03-a----------------------------------------------------
SELECT * 
FROM Carte
WHERE nr_pagini>=100 and nr_pagini<=200
ORDER BY nr_pagini,gen DESC;

	   -------------------------------------------16.03-b----------------------------------------------------

SELECT id_carte,id_imp,datai,datar,nr_zile,(TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy') )-nr_zile as "zile intarziate"
 FROM Imprumut
WHERE datar IS NOT NULL AND TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy')>nr_zile
ORDER BY "zile intarziate";

Sau

SELECT id_carte,id_imp,datai,datar,nr_zile,ROUND( MONTHS_BETWEEN(TO_DATE(datar, 'dd-Mon- yyyy'),TO_DATE(datai, 'dd-Mon-yyyy'))*30.5, 0) -nr_zile as "zile intarziate"
 FROM Imprumut
WHERE  datar IS NOT NULL AND ROUND( MONTHS_BETWEEN(TO_DATE(datar, 'dd-Mon- yyyy'),TO_DATE(datai, 'dd-Mon-yyyy'))*30.5, 0)>nr_zile
ORDER BY "zile intarziate";


	   -------------------------------------------16.04-a----------------------------------------------------

SELECT p.nume,concat('+',p.telefon) as telefon
FROM persoana p INNER JOIN  imprumut  i ON p.id_pers=i.id_imp
WHERE i.datar IS NULL AND TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(i.datai,'dd-Mon-yyyy')=30 ;

	   -------------------------------------------16.04-b----------------------------------------------------

SELECT '('||a.id_carte|| ',' ||b.id_carte|| ')' AS ID
FROM Carte a INNER JOIN  Carte b ON  a.gen=b.gen AND a.id_carte<b.id_carte;


	   -------------------------------------------16.05-a----------------------------------------------------
SELECT *
FROM Carte
WHERE nr_pagini in (select nr_pagini from carte where titlu='India')
ORDER BY id_carte;

	   -------------------------------------------16.05-b----------------------------------------------------

SELECT DISTINCT (id_carte)
FROM autor a
WHERE NOT  ((a.id_carte,a.id_aut) =all (SELECT id_carte,id_aut FROM autor  WHERE id_carte=a.id_carte))
ORDER BY id_carte;

	   -------------------------------------------16.06-a----------------------------------------------------
SELECT DISTINCT (titlu) 
FROM carte c JOIN  imprumut i ON i.id_carte=c.id_carte
WHERE  (SELECT count(*) FROM  imprumut WHERE i.id_carte=id_carte)>any(SELECT count(*)FROM imprumut GROUP BY id_carte);


  -------------------------------------------16.06-b----------------------------------------------------
SELECT gen, ROUND(MIN(NVL(nr_exemplare,0)),2) AS "Numar minim" ,ROUND(AVG(NVL(nr_exemplare,0)),2) AS "Numar mediu " ,ROUND(MAX(NVL(nr_exemplare,0)),2) AS "Numar maxim"
FROM carte
GROUP BY gen ;
	   
	   
	   -------------------------------------------16.07-a----------------------------------------------------
 insert into Persoana (id_pers,nume,telefon,adresa) values('26','Mircea Cartarescu’,021-1634567,NULL);
insert into Carte (id_carte,titlu,nr_pagini,nr_exemplare,gen) values(‘13’,'Visul',294,100,SF');
insert into Autor(id_carte,id_aut) values(13,26);
	   	   -------------------------------------------16.07-b----------------------------------------------------
DELETE 
FROM Autor 
WHERE id_carte NOT IN  (SELECT id_carte FROM Imprumut );

  	   -------------------------------------------16.07-c----------------------------------------------------
  	   
UPDATE Imprumut
SET nr_zile=nr_zile+TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy')
Where  datar IS NULL AND nr_zile<TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy');

	   -------------------------------------------16.08-a----------------------------------------------------
	   
 CREATE OR REPLACE TRIGGER adaugare_imprumut
BEFORE INSERT ON Imprumut FOR EACH ROW
DECLARE
v_nr_pagini NUMBER(30,2);
v_nr_zi  NUMBER(30,2);
v_nr_pagini_max Carte.nr_pagini%TYPE;
BEGIN
SELECT  max(nr_pagini) INTO v_nr_pagini_max FROM Carte;
SELECT nr_pagini INTO v_nr_pagini FROM Carte where id_carte=:NEW.id_carte;
v_nr_zi:=v_nr_pagini/(v_nr_pagini_max/15);
IF TRUNC(v_nr_zi,0)!=v_nr_zi then
v_nr_zi:= TRUNC(v_nr_zi,0)+1;
else
v_nr_zi:=TRUNC(v_nr_zi,0);
END IF;
IF :NEW.nr_zile  >15  OR  v_nr_zi>:NEW.nr_zile  THEN
RAISE_APPLICATION_ERROR(-20498,'Nr de zile pentru durata imprumutului sunt incorecte');
END IF;
END;

	   -------------------------------------------16.08-b----------------------------------------------------

CREATE OR REPLACE TRIGGER impiedica_adaugare
BEFORE INSERT ON Autor FOR EACH ROW
DECLARE
v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM Imprumut WHERE id_carte = :NEW.id_carte;
      IF v_count != 0  THEN 
        RAISE_APPLICATION_ERROR(-20002, 'Cartea este imprumutata,nu se pot adauga autori');
    END IF;
END;


  -------------------------------------------16.08-c----------------------------------------------------
  
  
  CREATE VIEW Carti_Beletristica AS
SELECT titlu, nr_pagini, nr_exemplare, nume AS autor, telefon
FROM Carte NATURAL JOIN Autor JOIN Persoana ON id_pers = id_aut
WHERE gen = 'BELETRISTICA';



  CREATE OR REPLACE  TRIGGER adugare_view
INSTEAD OF INSERT ON Carti_Beletristica 
DECLARE 
v_id_pers Persoana.id_pers%TYPE;
v_id_carte Carte.id_carte%TYPE;
BEGIN
SELECT count(*) into v_id_pers from Persoana;
v_id_pers:=v_id_pers+1;
SELECT count(*) into v_id_carte from Carte;
v_id_carte:=v_id_carte+1;
INSERT INTO Persoana(id_pers,nume,telefon,adresa) Values (v_id_pers,:NEW.autor,:NEW.telefon,NULL);
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (v_id_carte,:NEW.titlu,:NEW.nr_pagini,:NEW.nr_exemplare,'BELETRISTICA');
INSERT INTO Autor (id_carte,id_aut) VALUES (v_id_carte,v_id_pers);
END;

 