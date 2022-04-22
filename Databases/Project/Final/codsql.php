
<html>
    <head>
        <title>Codul SQL</title>

            <body bgcolor="lightgray">
        <style>
            .button {
                display: inline-block;
                padding: 15px 13px;
                font-size: 20px;
                cursor: pointer;
                text-align: center;
                text-decoration: none;
                color: black;
                background-color: pink;
                border: none;
                border-radius: 12px;
                box-shadow: 0 9px #999;
            }


.button:active {
background-color: white;
box-shadow: 0 5px #999;
}
        div {
                   border: 30px double;
                    text-align: center;
                    border-color: gray;
                }
        p {
            font-family: "Chalkduster", Times;
            font-size: 13px;
        }


table {
text-align:center;
margin: auto;
border: solid 2px;
border-color: black;
}

</style>
    </head>
    <body>
        <div>
            <p style="font-size: 30px; text-align: center">Codul SQL</p>
            <p style="text-align: center">
--------Subiectul 16------
<br>
<br> -----16.01-----
<br> Tabelele sunt: Persoana, Carte, Imprumut ,Autor
<br>---a---
<br>CREATE TABLE Persoana(id_pers INT NOT NULL,
nume VARCHAR(50),
telefon VARCHAR(20),
adresa VARCHAR(50));
<br>---b---
<br>CREATE TABLE  Carte (id_carte INT NOT NULL,
titlu VARCHAR(50),
nr_pagini NUMBER(20),
nr_exemplare NUMBER(20),
gen VARCHAR(30),
rezumat VARCHAR(50));
<Br>---c---
<br>CREATE TABLE Imprumut (id_carte INT NOT NULL,
id_imp INT NOT NULL,
datai DATE NOT NULL,
datar DATE,
nr_zile NUMBER(30));
<br>---d---
<br>CREATE TABLE Autor (id_carte INT NOT NULL,
id_aut INT NOT NULL);
<br>---e---
<br>ALTER TABLE Persoana ADD CONSTRAINT Pk_id_pers PRIMARY KEY (id_pers);

<br>ALTER TABLE Carte ADD CONSTRAINT Pk_id_carte PRIMARY KEY (id_carte);

<br>ALTER TABLE Imprumut ADD CONSTRAINT Pk_imprumut PRIMARY KEY (id_carte,id_imp,datai);

<br>ALTER TABLE Autor ADD CONSTRAINT Pk_autor PRIMARY KEY (id_carte,id_aut);

    <br>ALTER TABLE Imprumut ADD
    CONSTRAINT FK_id_imp FOREIGN KEY (id_imp) REFERENCES Persoana(id_pers);
    <br>ALTER TABLE Imprumut ADD
    CONSTRAINT FK_imp_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
    <br>ALTER TABLE Autor  ADD
        CONSTRAINT FK_aut_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
    <br>ALTER TABLE Autor  ADD
        CONSTRAINT FK_aut_id_autor FOREIGN KEY (id_aut) REFERENCES Persoana (id_pers);
<br>---f---
<br>ALTER TABLE Carte
    DROP COLUMN rezumat;
    <br>
<br>-----16.02----
<br>---a---
<br> ALTER TABLE Carte ADD
CONSTRAINT CK_nr_pg CHECK (nr_pagini>=10 and nr_pagini<=1000);
<br>---b---
<br>ALTER TABLE Persoana ADD
CONSTRAINT CK_pers_adresa CHECK ( telefon not like '+40%' or  (telefon like '+40%' and adresa like 'Romania'));

<br>
<br>-----16.03----
<br>---a---
<br>SELECT *
FROM Carte
WHERE nr_pagini>=100 and nr_pagini<=200
ORDER BY nr_pagini,gen DESC;
<br>---b---
<br>SELECT id_carte,id_imp,datai,datar,nr_zile,(TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy') )-nr_zile as "zile intarziate"
FROM Imprumut
WHERE datar IS NOT NULL AND TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy')>nr_zile
ORDER BY "zile intarziate";

<br>
<br>-----16.04-----
<br>---a---
<br>SELECT p.nume,concat('+',p.telefon) as telefon
FROM persoana p INNER JOIN  imprumut  i ON p.id_pers=i.id_imp
WHERE i.datar IS NULL AND TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(i.datai,'dd-Mon-yyyy')=30 ;
<br>---b---
<br>SELECT '('||a.id_carte|| ',' ||b.id_carte|| ')' AS ID
FROM Carte a INNER JOIN  Carte b ON  a.gen=b.gen AND a.id_carte<b.id_carte;
<br>
<br>
<br>-----16.05-----
<br>---a---
<br>SELECT *
FROM Carte
WHERE nr_pagini in (select nr_pagini from carte where titlu='India')
ORDER BY id_carte;


<br>---b---
<br>SELECT DISTINCT (id_carte)
FROM autor a
WHERE NOT  ((a.id_carte,a.id_aut) =all (SELECT id_carte,id_aut FROM autor  WHERE id_carte=a.id_carte))
ORDER BY id_carte;


<br>-----16.06-----
<br>---a---
<br>SELECT DISTINCT (titlu)
FROM carte c JOIN  imprumut i ON i.id_carte=c.id_carte
WHERE  (SELECT count(*) FROM  imprumut WHERE i.id_carte=id_carte)>any(SELECT count(*)FROM imprumut GROUP BY id_carte);

<br>---b---
<br>SELECT gen,MIN(NVL(nr_exemplare,0)) AS "Numar minim" ,ROUND(AVG(NVL(nr_exemplare,0)),2) AS "Numar mediu " ,MAX(NVL(nr_exemplare,0)) AS "Numar maxim"
FROM carte
GROUP BY gen ;
       
       <br>
<br>-----16.07-----
<br>---a---
<br> insert into Persoana (id_pers,nume,telefon,adresa) values('26','Mircea Cartarescu’,021-1634567,NULL);
insert into Carte (id_carte,titlu,nr_pagini,nr_exemplare,gen) values(‘13’,'Visul',294,100,SF');
insert into Autor(id_carte,id_aut) values(13,26);
<br>---b---
<br>DELETE
FROM Autor
WHERE id_carte NOT IN  (SELECT id_carte FROM Imprumut );

<br>---c---
<br>
UPDATE Imprumut
SET nr_zile=nr_zile+TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy')
<br>Where  datar IS NULL AND AND nr_zile<TO_DATE(SYSDATE,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy');
<br>
<br>
<br>-----16.08-----
<br>---a---
<br>CREATE OR REPLACE TRIGGER adaugare_imprumut
<br>BEFORE INSERT ON Imprumut FOR EACH ROW
<br>DECLARE
<br>v_nr_pagini_max Carte.nr_pagini%TYPE;
<br>v_nr_pagini NUMBER(30,2);
<br>v_nr_zi  NUMBER(30,2);
<br>BEGIN
<br>SELECT  max(nr_pagini) INTO v_nr_pagini_max FROM Carte;
<br>SELECT nr_pagini INTO v_nr_pagini FROM Carte where id_carte=:NEW.id_carte;
<br>v_nr_zi:=v_nr_pagini/(v_nr_pagini_max/15);
<br>IF TRUNC(v_nr_zi,0)!=v_nr_zi then
<br>v_nr_zi:= TRUNC(v_nr_zi,0)+1;
<br>else
<br>v_nr_zi:=TRUNC(v_nr_zi,0);
<br>END IF;
<br>IF :NEW.nr_zile  >15  OR  v_nr_zi>:NEW.nr_zile  THEN
<br>RAISE_APPLICATION_ERROR(-20498,'Nr de zile pentru durata imprumutului sunt incorecte');
<br>END IF;
<br>END;

<br>---b---
<br>CREATE OR REPLACE TRIGGER impiedica_adaugare
<br>BEFORE INSERT ON Autor FOR EACH ROW
<br>DECLARE
<br>v_count NUMBER :=0;
<br>BEGIN
<br> SELECT COUNT(*) INTO v_count FROM Imprumut WHERE id_carte = :NEW.id_carte;
<br>  IF v_count != 0  THEN
<br>  RAISE_APPLICATION_ERROR(-20002, 'Cartea este imprumutata,nu se pot adauga autori');
<br> END IF;
<br>END;

<br>---c---
<br>CREATE VIEW Carti_Beletristica AS
<br>SELECT titlu, nr_pagini, nr_exemplare, nume AS autor, telefon
<br>FROM Carte NATURAL JOIN Autor JOIN Persoana ON id_pers = id_aut
<br>WHERE gen = 'BELETRISTICA';


<br>
<br> CREATE OR REPLACE  TRIGGER adugare_view
<br>INSTEAD OF INSERT ON Carti_Beletristica
<br>DECLARE
<br>v_id_pers Persoana.id_pers%TYPE;
<br>v_id_carte Carte.id_carte%TYPE;
<br>BEGIN
<br>SELECT count(*) into v_id_pers from Persoana;
<br>v_id_pers:=v_id_pers+1;
<br>SELECT count(*) into v_id_carte from Carte;
<br>v_id_carte:=v_id_carte+1;
<br>INSERT INTO Persoana(id_pers,nume,telefon,adresa) Values (v_id_pers,:NEW.autor,:NEW.telefon,NULL);
<br>INSERT INTO <br>Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (v_id_carte,:NEW.titlu,:NEW.nr_pagini,:NEW.nr_exemplare,'BELETRISTICA');
<br>INSERT INTO Autor (id_carte,id_aut) VALUES (v_id_carte,v_id_pers);
<br>END;

 
            </p>
                                                                     <a href="index.php" class="button">Back</a>
            <br><br>
        </div>
   
    </body>

</html>
