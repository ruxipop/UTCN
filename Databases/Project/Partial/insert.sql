

CREATE TABLE Persoana(id_pers INT NOT NULL,
                      nume VARCHAR(30),
                      telefon VARCHAR(20),
                      adresa VARCHAR(50));


CREATE TABLE  Carte (id_carte INT NOT NULL,
                      titlu VARCHAR(50),
                      nr_pagini NUMBER(20),
                      nr_exemplare NUMBER(20),
                      gen VARCHAR(30),
                      rezumat VARCHAR(50));
                      
                      
  CREATE TABLE Imprumut (id_carte INT NOT NULL,
                       id_imp INT NOT NULL,
                       datai DATE NOT NULL,
                       datar DATE,
                       nr_zile NUMBER(30));
                       
    CREATE TABLE Autor (id_carte INT NOT NULL,
                    id_aut INT NOT NULL);                    
                      

ALTER TABLE Persoana ADD CONSTRAINT Pk_id_pers PRIMARY KEY (id_pers);
ALTER TABLE Carte ADD CONSTRAINT Pk_id_carte PRIMARY KEY (id_carte);
ALTER TABLE Autor ADD CONSTRAINT Pk_autor PRIMARY KEY (id_carte,id_aut);
ALTER TABLE Imprumut ADD CONSTRAINT Pk_imprumut PRIMARY KEY (id_carte,id_imp,datai);

 
 ALTER TABLE Autor  ADD 
	CONSTRAINT FK_aut_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
 ALTER TABLE Autor  ADD 
	CONSTRAINT FK_aut_id_autor FOREIGN KEY (id_aut) REFERENCES Persoana (id_pers);
 ALTER TABLE Imprumut ADD 
	CONSTRAINT FK_id_imp FOREIGN KEY (id_imp) REFERENCES Persoana(id_pers);
ALTER TABLE Imprumut ADD 
	CONSTRAINT FK_imp_id_carte FOREIGN KEY (id_carte) REFERENCES Carte(id_carte);
	
	
ALTER TABLE Carte
DROP COLUMN rezumat;




INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (1,'George Pop',+1948203745,'America');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (2,'Maria Oros',   +4003021342,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (3,'Veronica Sand',   +330302134,'Franta');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (4,'Andrei Popescu',   +407352861,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (5,'Margaret Aidan',   +347225141,'Spania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (6,'Charles Plant',   +447227541,'Anglia');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (7,'Dora Berinde',   +417225341,'Elvetia');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (8,'Peter Mihoc ',   +327234561,'Belgia');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (9,'Anton Marian',   +431344231,'Austria');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (10,'Izabela Balta',   +417901411,'Elvetia');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (11,'Irina Mihaila ',   +407111141,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (12,'Frank Herbert',   +194179011,'America');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (13,'Mircea Eliade',   +404239029,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (14,'Yann Martel',   +123239487,'Canda');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (15,'Veronica Roth',   +123210097,'America');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (16,'Charles Wells',   +445567321,'Regatul Unit');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (17,'Elizabeth Gilbert',   +144659321,'America');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (18,'Elena Gilbert',   +144659321,'America');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (19,'John Ronald',   +441928411,'Regatul Unit');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (20,'Marin Preda',   +406284951,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (21,'George Wells',   +442387273,'Regatul Unit');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (22,'Adarsh Jai',   +912484883,'India');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (23,'Ion Creaga',   +403848382,'Romania');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (24,'Honore de Balzac',   +331092483,'Franta');
INSERT INTO Persoana (id_pers,nume,telefon,adresa) VALUES (25,'Joanne Rowling',   +441474783,'Regatul Unit');



INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (1,'Dune',500,20,'SF');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (2,'Divergent',198,11,'SF');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (3,'Maitryi',120,15,'Dragoste');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (4,'Cel mai iubit dintre pamanteni',900,9,'Beletristica');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (5,'Razboiul Luminilor',120,20,'SF');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (6,'Hobbit',170,10,'Actiune');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (7,'Amintiri din copilarie ',132,100,'Aventura');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (8,'India',120,9,'Istorie');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (9,'Pielea de sagri',180,9,'Beletristica');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (10,'Viata lui pi',200,2,'Aventura');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (11,'Mostenirea',80,26,'Beletristica');
INSERT INTO Carte(id_carte,titlu,nr_pagini,nr_exemplare,gen) VALUES (12,'Harry Potter ',400,70,'Aventura');

INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (1,9,to_date('10-Feb-2020','dd-Mon-yyyy'),NULL,4);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (2,1,to_date('15-Oct-2020','dd-Mon-yyyy'),to_date('20-Oct-2020','dd-Mon-yyyy'),1);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (2,8,to_date('09-Aug-2020','dd-Mon-yyyy'),NULL,9);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (3,3,to_date('10-Jan-2020','dd-Mon-yyyy'),to_date('20-Jan-2020','dd-Mon-yyyy'),5);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (6,2,to_date('18-Mar-2020','dd-Mon-yyyy'),to_date('29-Mar-2020','dd-Mon-yyyy'),2);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (6,10,to_date('23-Jun-2020','dd-Mon-yyyy'),NULL,9);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (6,7,to_date('23-Jan-2020','dd-Mon-yyyy'),to_date('10-Mar-2020','dd-Mon-yyyy'),20);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (7,4,to_date('01-Dec-2020','dd-Mon-yyyy'),to_date('10-Dec-2020','dd-Mon-yyyy'),10);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (9,9,to_date('10-Oct-2020','dd-Mon-yyyy'),NULL,12);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (10,10,to_date('06-Nov-2020','dd-Mon-yyyy'),NULL,12);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (11,5,to_date('13-May-2020','dd-Mon-yyyy'),to_date('14-May-2020','dd-Mon-yyyy'),4);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (12,8,to_date('13-Jun-2020','dd-Mon-yyyy'),to_date('18-Jun-2020','dd-Mon-yyyy'),2);
INSERT INTO Imprumut(id_carte,id_imp,datai,datar,nr_zile) VALUES (12,6,to_date('22-Jul-2020','dd-Mon-yyyy'),NULL,10);



INSERT INTO Autor (id_carte,id_aut) VALUES(1,12);
INSERT INTO Autor (id_carte,id_aut) VALUES(2,15);
INSERT INTO Autor (id_carte,id_aut) VALUES(3,13);
INSERT INTO Autor (id_carte,id_aut) VALUES(4,20);
INSERT INTO Autor (id_carte,id_aut) VALUES(5,21);
INSERT INTO Autor (id_carte,id_aut) VALUES(5,16);
INSERT INTO Autor (id_carte,id_aut) VALUES(6,19);
INSERT INTO Autor (id_carte,id_aut) VALUES(7,23);
INSERT INTO Autor (id_carte,id_aut) VALUES(8,22);
INSERT INTO Autor (id_carte,id_aut) VALUES(9,24);
INSERT INTO Autor (id_carte,id_aut) VALUES(10,14);
INSERT INTO Autor (id_carte,id_aut) VALUES(11,17);
INSERT INTO Autor (id_carte,id_aut) VALUES(11,18);
INSERT INTO Autor (id_carte,id_aut) VALUES(12,25);


 ALTER TABLE Carte ADD 
	CONSTRAINT CK_nr_pg CHECK (nr_pagini>=10 and nr_pagini<=1000);
	
		ALTER TABLE Persoana ADD 
	CONSTRAINT CK_pers_adresa CHECK ( telefon !='+40%' OR (telefon='+40%' and adresa='Romania'));
