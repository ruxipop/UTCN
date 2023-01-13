------------------------------------Constrangeri----------------------------------

alter table AdministratorBiblioteca
add constraint experienta0
default 0 for Experienta

alter table CititoriCartiImprumutate
add constraint cartiimpr1
default 1 for NrCartiImprumutate


------------------------------------Indecsi----------------------------------

-- if exists( select name from sys.indexes where name = 'indexCarti')
-- drop index indexCarti on CartiImprumutate
create nonclustered index indexCarti
 on CartiImprumutate(nrCopiiDeCarti)

--nu sunt permise valori duplicate pentru AdresaB
--o sg biblioteca la o anumita adresa

CREATE UNIQUE INDEX index_adresa_biblioteca
ON Biblioteci (AdresaB);

--sunt permise valori duplicate pentru idGen din tabela Carti
--mai multi carti pot sa aibe acelasi gen
CREATE INDEX index_idgen_carti
ON Carti (IdGen);

--clustered cum sunt sortati in baza de date
--sorteaza in ordine ascendenta cartile is functie de nrSerie
--exista deja un index clustered pt aceasta baza de date pt cheia primara
--trebuie sters acela pt a crea altul

CREATE CLUSTERED INDEX index_clustered
ON Carti(NrSerie ASC)

--pentru a sterge un indecs
DROP INDEX Biblioteci.index_adresa_biblioteca;
--pentru a vizualiza indecsi unui tabel

EXECUTE sp_helpindex Biblioteci



------------------------------------Vederi----------------------------------

--1
--afiseaza cititorii care au carti imprumutate, varsta > 10, si nr. de carti existente < 12
GO
CREATE OR ALTER VIEW carti_imprumutate_in_functie_varsta_carti AS
SELECT c.IdCititor, c.Varsta, cci.IdCarteImprum, ci.nrCopiiDeCarti
FROM Cititori c 
INNER JOIN CititoriCartiImprumutate cci ON c.IdCititor = cci.IdCititor
INNER JOIN CartiImprumutate ci ON ci.IdCarteImprum = c.IdCititor
WHERE c.varsta > 10
GROUP BY c.IdCititor, c.Varsta, cci.IdCarteImprum, ci.nrCopiiDeCarti
HAVING nrCopiiDeCarti < 12
GO
SELECT * FROM carti_imprumutate_in_functie_varsta_carti ;


--2
--afiseaza genul cartilor care au fost imprumutate, unde nr. de carti cu acelasi gen este > 35 si nr. de pagini a unei carti este < 260
GO
CREATE OR ALTER VIEW carti_gen_imprumutate_nr_carti AS
SELECT g.IdGen, g.Gen, c.Titlu, c.NrPagini
FROM GenulCartii g 
INNER JOIN Carti c ON g.IdGen = c.IdGen
INNER JOIN GenulCartiiImprumutate gci ON gci.IdGen= g.IdGen
WHERE NrCartiCuAcelasiGen > 35
GROUP BY  g.IdGen,  g.Gen, c.Titlu, c.NrPagini
HAVING c.NrPagini < 260
GO
SELECT * FROM carti_gen_imprumutate_nr_carti;
 SELECT * FROM Carti;
 SELECT * FROM GenulCartii

--3
--afiseaza id-ul cartilor care au genul Mister/Fictiune 
GO
CREATE OR ALTER VIEW carti_gen_mister_fictiune AS
SELECT c.IdCarte, c.Titlu, c.Autor, c.NrPagini, gc.Gen, gc.NrCartiCuAcelasiGen
FROM Carti c 
INNER JOIN CartiBiblioteci cb ON c.IdCarte = cb.IdCarte
INNER JOIN GenulCartii gc ON gc.IdGen = c.IdGen
WHERE gc.Gen IN ('Mister', 'Fictiune')
GROUP BY c.IdCarte, c.Titlu, c.Autor, c.NrPagini, gc.Gen, gc.NrCartiCuAcelasiGen
GO
SELECT * FROM carti_gen_mister_fictiune;

--4
--afiseaza administratorii de biblioteca cu Experienta de peste 10 ani sau a caror Prenume incep cu litera A si e urmat de cel putin 2 caractere
GO
CREATE  OR ALTER VIEW admin_experiente_prenume AS
SELECT a.IdAdmin, a.Nume, a.Prenume, a.Experienta
FROM AdministratorBiblioteca a INNER JOIN Biblioteci b ON a.IdAdmin = b.IdBiblioteca
WHERE a.Experienta > 10 OR Prenume  = 'A_%'
GO
SELECT * FROM admin_experiente_prenume;

--5
--afiseaza administratorii de biblioteca cu Experienta mai mica de 15  si intervalul de functionare a biblioteci e respectat
GO
CREATE OR ALTER VIEW  admin_experiente_prenume_biblioteca AS
SELECT a.IdAdmin, a.Nume, a.Prenume, a.Experienta, b.IdBiblioteca, b.OrarBiblioteca
FROM AdministratorBiblioteca a 
INNER JOIN Biblioteci b ON a.IdAdmin = b.IdBiblioteca
WHERE a.Experienta <15  AND b.OrarBiblioteca = '08:30 - 20:30';
GO
SELECT * FROM admin_experiente_prenume_biblioteca;


------------------------------------Proceduri----------------------------------

--1
--afiseaza toate cartile dintr-o biblioteca a carui adresa este data ca parametru
GO
CREATE OR ALTER PROCEDURE carti_bilioteca @adresa VARCHAR(50) AS
SELECT c.IdCarte, c.Titlu , c.Autor ,c.NrSerie , b.AdresaB as 'Adresa biblioteca' FROM Carti c
JOIN CartiBiblioteci cb on c.IdCarte=cb.IdCarte
JOIN Biblioteci B ON cb.IdBiblioteca=b.IdBiblioteca
WHERE b.AdresaB = @adresa

EXEC carti_bilioteca @adresa='Centru'

--2 
--angajarea unui adminsitrator pentru biblioteca a carui adresa e data ca parametru, cu  numele si prenume date ca parametru ,daca nu exista deja un angajat
--daca exista deja un administartor se returneaza un mesaj corespunzator

GO
CREATE OR ALTER PROCEDURE angajare_admistrator @nume VARCHAR(50) , @prenume VARCHAR(50), @adresa_bilioteca VARCHAR(50) AS
DECLARE @id_administrator int;
IF   EXISTS (SELECT * FROM AdministratorBiblioteca ab JOIN Biblioteci b on ab.IdAdmin=b.IdBiblioteca WHEre b.AdresaB=@adresa_bilioteca)
BEGIN
 PRINT ('Exista un adminstrator pentru aceasta biblioteca!')
END ELSE
BEGIN
   SELECT @id_administrator= b.IdBiblioteca from Biblioteci b WHERE b.AdresaB=@adresa_bilioteca;
   INSERT INTO AdministratorBiblioteca VALUES (@id_administrator,@nume,@prenume,0);
END

EXEC  angajare_admistrator @nume='Pop', @prenume='Ioan',@adresa_bilioteca='Mihai Viteazu'


--3
--procedura care sterge cititori cu o varsta mai mica decat cea data ca parametru
INSERT INTO Cititori VALUES('ION','POPESCU', 7, 0938,'ION')

GO
CREATE OR ALTER PROCEDURE stergere_cititori @varsta INT AS
IF EXISTS ( SELECT * FROM Cititori WHERE Varsta<@varsta)
BEGIN
DELETE FROM Cititori WHERE Varsta<@varsta;
END ELSE
BEGIN
 PRINT ('Nu exista cititori cu varsta mai mica de !')
END

EXEC stergere_cititori  @varsta=8

--4
--afiseaza  cartiile care au genul dat ca parametru 
GO
CREATE OR ALTER PROCEDURE afisare_carti @gen_carte VARCHAR(50) AS
SELECT c.IdCarte, c.Titlu, c.Autor, c.NrPagini, gc.Gen, gc.NrCartiCuAcelasiGen
FROM Carti c 
INNER JOIN CartiBiblioteci cb ON c.IdCarte = cb.IdCarte
INNER JOIN GenulCartii gc ON gc.IdGen = c.IdGen
WHERE gc.Gen=@gen_carte
GROUP BY c.IdCarte, c.Titlu, c.Autor, c.NrPagini, gc.Gen, gc.NrCartiCuAcelasiGen

EXEC afisare_carti @gen_carte='Mister'


------------------------------------Triggere DML----------------------------------
--manipulezi datele


--1
--atunci cand se sterge o carte din tabelul Carti se sterge cartea si din Tabela CartiBilioteci

INSERT INTO Carti VALUES('Viata','Acante',44,500,2)
INSERT INTO CartiBiblioteci  values (1,(SELECT IdCarte FROM Carti WHERE Titlu='Viata'))

GO
CREATE OR ALTER TRIGGER stergere_carte_biblioteca ON Carti
INSTEAD OF DELETE AS 
DELETE FROM CartiBiblioteci 
WHERE IdCarte IN (SELECT deleted.IdCarte FROM deleted)
DELETE FROM Carti
WHERE IdCarte IN (SELECT deleted.IdCarte FROM deleted)

DELETE FROM Carti WHERE IdCarte=(SELECT IdCarte FROM Carti WHERE Titlu='Viata');


--2
--afisare mesaj in cazul in care se creaza o noua Biblioteca cu un numar de citori mai mic sau egal cu 2
GO
CREATE OR ALTER TRIGGER creare_biblioteca ON Biblioteci
AFTER INSERT AS
IF (SELECT i.NrCititori FROM inserted i) <=2
BEGIN
PRINT ('Numarul de cititori este prea mic!');
	ROLLBACK TRANSACTION;
END
GO

INSERT INTO Biblioteci VALUES ('Buna ziua','10:00 - 12:00 ',1)

--3
--administratori cu o experienta mai mare de 5 ani sa nu poate fi concediati


GO
CREATE OR ALTER TRIGGER concediere_angajati on AdministratorBiblioteca
INSTEAD  of DELETE as
BEGIN 
  declare @id_admin bigint
  select @id_admin=deleted.IdAdmin from deleted
  if @id_admin in (select IdAdmin from  AdministratorBiblioteca where Experienta>=5)
  begin 
     PRINT ('Adminstratorul are o experienta mai mare de 5 ani !');
     ROLLBACK TRANSACTION;
  end;
  else
    begin 
	  delete from AdministratorBiblioteca
	     where IdAdmin=@id_admin
	end;
end;
DELETE FROM AdministratorBiblioteca WHERE IdAdmin=1;


--4
--la stergerea unei biblioteci toate cartile care au genul Mister sunt transfera la biblioteca din Grigorescu
--restul se sterg


------------------------------------Triggere DDL----------------------------------
--pt a defini structuri de date (create alter table)

--1
--la stergerea tabelelor din baza de date curenta sa apara un mesaj de avertisment
GO
CREATE OR ALTER TRIGGER DDL_stergere_Tabele
ON DATABASE FOR DROP_TABLE AS
BEGIN
PRINT ( 'Atentie , ai sters un tabel!')
END

CREATE TABLE Persons (
    PersonID int,
    LastName varchar(255),
    FirstName varchar(255),
    Address varchar(255),
    City varchar(255) 
);

DROP TABLE Persons;
-- select * from Persons;

--2
--sa fie interzisa modificarea tabelelor bazei de date curente
GO
CREATE OR ALTER TRIGGER interzicere_modificare  
ON DATABASE FOR  ALTER_TABLE  AS
BEGIN
PRINT('Este interzis sa modifici un tabel din baza de date!');
ROLLBACK TRANSACTION
END

ALTER TABLE AdministratorBiblioteca ADD adresa VARCHAR(500)

--3
--la crearea unei baze de date se afiseaza un mesaj 
GO
CREATE OR ALTER TRIGGER creare_db
ON ALL SERVER
FOR CREATE_DATABASE as
BEGIN
PRINT 'Ai creat o baza de date!' 
END

create database "b";


--4
--cand se creeaza o tabela noua, se afiseaza toate tabelele din baza de date curenta
GO
CREATE OR ALTER TRIGGER   creare_table
ON DATABASE FOR create_table as 
SELECT name, create_date from sys.tables 

CREATE TABLE Persons (
    PersonID int,
    LastName varchar(255),
    FirstName varchar(255),
    Address varchar(255),
    City varchar(255) 
);

--5
--nu poti sa schimbi numele unui tabel
GO
CREATE OR ALTER TRIGGER rename_table
ON DATABASE FOR  RENAME AS
BEGIN
    PRINT ('Nu poti sa schimbi numele unui tabel')
    ROLLBACK TRANSACTION
END

GO
sp_rename 'Persons', 'Persons2'

-- SELECT * FROM Persons

------------------------------------Cursoare----------------------------------

--1
--afiseaza cititori, datile in care au imprumutat respectiv returnat cartile, si nr de carti returnate
GO
DECLARE cursor_carti SCROLL CURSOR FOR
SELECT c.Nume +c.Prenume AS 'Cititor', ci.DataImprumut as 'Data imprumut', ci.DataReturn as 'Data returnare', cci.NrCartiImprumutate as 'Numarul carti imprumutate'
FROM Cititori c 
INNER JOIN CititoriCartiImprumutate cci ON c.IdCititor = cci.IdCititor
INNER JOIN CartiImprumutate ci ON ci.IdCarteImprum = c.IdCititor

OPEN cursor_carti

DECLARE @nume_cititor varchar(90), @data_imprumut varchar(50), @data_return varchar(60),
	    @nr_carti_imp VARCHAR(90)

FETCH FIRST FROM cursor_carti INTO @nume_cititor, @data_imprumut,@data_return, @nr_carti_imp;

While @@FETCH_STATUS=0 
Begin
 Print ('Cititor: '+@nume_cititor+'; Data  Imprumut : '+@data_imprumut+'; Data Return : '+@data_return+';NrCartiImprumutate '+@nr_carti_imp);
 Fetch next from  cursor_carti into @nume_cititor, @data_imprumut,@data_return, @nr_carti_imp ;
End;
 
Close cursor_carti;
Deallocate cursor_carti;


--2
--afiseaza primele 3 tabele din baza de date

-- declaram variabile folosite in cursor
DECLARE @table_name VARCHAR(128);
DECLARE @table_names_3 VARCHAR(128);
 

DECLARE cursor_table_names CURSOR FOR
  SELECT TOP 3 TABLE_NAME 
  FROM INFORMATION_SCHEMA.TABLES
  ORDER BY TABLE_NAME ASC;
 
SET @table_names_3 = 'first 3 tables are: '

OPEN cursor_table_names;
 
FETCH NEXT FROM cursor_table_names INTO @table_name;
WHILE @@FETCH_STATUS = 0
    BEGIN
    IF @table_names_3 = 'first 3 tables are: '
      SET @table_names_3 = CONCAT(@table_names_3, @table_name)
    ELSE
      SET @table_names_3 = CONCAT(@table_names_3, ', ', @table_name);     
    FETCH NEXT FROM cursor_table_names INTO @table_name;
    END;
PRINT @table_names_3;

CLOSE cursor_table_names;
DEALLOCATE cursor_table_names;


---------------------------------------UTILIZATORI----------------------------------

---Adminstrator --administratorul bilioteci 

CREATE LOGIN Administrator1
WITH PASSWORD = 'LumeaCartilor1';
GO

CREATE USER Administrator1 FOR LOGIN Administrator1;  
GO 

exec sp_addrolemember 'db_owner', 'Administrator1'; 

GRANT DELETE,INSERT,UPDATE,SELECT ON DATABASE::LumeaCartilor TO Administrator1;

EXECUTE AS LOGIN = 'Administrator1'; 
Select *  from Carti;
REVERT;

---Operator Backup --angajat care se ocupa de integritatea datelor

CREATE LOGIN OperatorBackup1
WITH PASSWORD = 'LumeaCartilor1';
GO

CREATE USER OperatorBackup1 FOR LOGIN OperatorBackup1;  
GO 

exec sp_addrolemember 'db_backupoperator', 'OperatorBackup1';  

EXECUTE AS LOGIN = 'OperatorBackup1'; 
REVERT;


---Angajat --simplu angajat al biblioteci 

CREATE LOGIN Angajat1
WITH PASSWORD = 'LumeaCartilor1';
GO

CREATE USER Angajat1 FOR LOGIN Angajat1;  
GO 

exec sp_addrolemember 'db_datareader', 'Angajat1'; 


GRANT DELETE,INSERT ON DATABASE::LumeaCartilor TO Angajat1;
DENY SELECT ON DATABASE::LumeaCartilor TO Angajat1;

GRANT EXEC TO Angajat1;
EXECUTE AS LOGIN = 'Angajat1'; 
SELECT*  from Carti;


INSERT INTO GenulCartii VALUES('Istorie','Descriere19',7)
REVERT;

--selecteaza user-ul current

SELECT CURRENT_USER;






SELECT DP1.name AS DatabaseRoleName,   
   isnull (DP2.name, 'No members') AS DatabaseUserName   
 FROM sys.database_role_members AS DRM  
 RIGHT OUTER JOIN sys.database_principals AS DP1  
   ON DRM.role_principal_id = DP1.principal_id  
 LEFT OUTER JOIN sys.database_principals AS DP2  
   ON DRM.member_principal_id = DP2.principal_id  
WHERE DP1.type = 'R'
ORDER BY DP1.name;  




------------------------------------JOB-URI----------------------------------

--inseram intr-o tabela noua toate cartile care au fost imprumutate in ziua curenta--

CREATE TABLE CARTI_IMPRUMUTATE_AZI(IdCarteImprum int primary key,
                                   Titlu VARCHAR (50),
                                   Autor VARCHAR(50),
                                   nrCopiiDeCarti int
                                   )


GO

CREATE OR ALTER PROCEDURE imprumutate_azi AS
INSERT INTO CARTI_IMPRUMUTATE_AZI (IdCarteImprum,Titlu,Autor,nrCopiiDeCarti)
SELECT ci.IdCarteImprum , c.Titlu, c.Autor, ci.nrCopiiDeCarti FROM CartiImprumutate ci
JOIN GenulCartiiImprumutate gci ON ci.IdCarteImprum=gci.IdCarteImprum 
JOIN GenulCartii gc ON gc.IdGen=gci.IdGen 
JOIN Carti c ON c.IdCarte=gc.IdGen
WHERE YEAR(ci.DataImprumut)=YEAR(GETDATE()) and MONTH(ci.DataImprumut)=MONTH(GETDATE()) and day(ci.DataImprumut)=DAY(GETDATE())
GO

USE msdb ;  
GO  
EXEC dbo.sp_add_job  
    @job_name = N'Inserare_carti' ;  
GO  
EXEC sp_add_jobstep  
    @job_name = N'Inserare_carti',  
    @step_name = N'Executare_proc',  
    @subsystem = N'TSQL',  
    @command = N'exec imprumutate_azi', 
    @database_name = N'LumeaCartilor',
    @retry_attempts = 5,  
    @retry_interval = 5 ;  
GO  
EXEC dbo.sp_add_schedule  
    @schedule_name = N'RunDaily',  
    @freq_type = 4,  
	@freq_interval = 1,
    @active_start_date = 20211128 ,
	@active_start_time = 210000 ;
USE msdb ;  
GO  
EXEC sp_attach_schedule  
   @job_name = N'Inserare_carti',  
   @schedule_name = N'RunDaily';  
GO  
EXEC dbo.sp_add_jobserver  
    @job_name = N'Inserare_carti';  



-- SELECT * from CARTI_IMPRUMUTATE_AZI;
-- DELETE  from  CARTI_IMPRUMUTATE_AZI




------------------------------------BACK-UP----------------------------------


USE msdb ;  
GO  
EXEC dbo.sp_add_job  
    @job_name = N'Backup_periodic' ;  
GO  
EXEC sp_add_jobstep  
    @job_name = N'Backup_periodic',  
    @step_name = N'Database_backup',  
    @subsystem = N'TSQL',  
    @command = N'backup database LumeaCartilor to disk = ''/Users/popruxi/Desktop/proiect_abd/''', 
	  @database_name = N'LumeaCartilor',
    @retry_attempts = 5,  
    @retry_interval = 5 ;  
GO  
EXEC dbo.sp_add_schedule  
    @schedule_name = N'DailyRun',  
    @freq_type = 4,
	  @freq_interval = 1,
	  @active_start_time = 220000 ;
USE msdb ;  
GO  
EXEC sp_attach_schedule  
   @job_name = N'Backup_periodic',  
   @schedule_name = N'DailyRun';  
GO  
EXEC dbo.sp_add_jobserver  
    @job_name = N'Backup_periodic';  
GO  


