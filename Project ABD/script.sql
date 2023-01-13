------------------------------------DATABASE----------------------------------
create database LumeaCartilor
go
use LumeaCartilor
go


------------------------------------TABELE----------------------------------
create table GenulCartii(
IdGen int primary key identity,
Gen varchar(50)  check ( Gen='Actiune' or Gen='Dragoste' or Gen='Fictiune' or Gen='Mister' or Gen='Istorie'),
Descriere varchar(100),
NrCartiCuAcelasiGen int
)

create table Carti(
IdCarte int primary key identity, 
Titlu varchar(50) NOT NULL,
Autor varchar(50), 
NrSerie int,
NrPagini int check (NrPagini > 20),
IdGen int foreign key references GenulCartii(IdGen)
)


create table Biblioteci(
IdBiblioteca int primary key identity(1,1),
AdresaB varchar(50),
OrarBiblioteca varchar(50),
NrCititori int
)

create table CartiBiblioteci(
IdBiblioteca int foreign key references Biblioteci(IdBiblioteca),
IdCarte int foreign key references Carti(IdCarte),
constraint pk_CartiBiblioteci primary key (IdBiblioteca, IdCarte)
)

create table AdministratorBiblioteca (
IdAdmin int foreign key references Biblioteci(IdBiblioteca), 
Nume varchar(50),
Prenume varchar(50),
Experienta int, 
constraint pk_AdministratorBiblioteca primary key (IdAdmin)
)

create table Cititori (
IdCititor int primary key identity, 
Nume varchar(50),
Prenume varchar(50),
Varsta int,
NrDeTelefon int,
Email varchar(50),
)

create table CartiImprumutate(
IdCarteImprum int primary key identity,
DataImprumut DATE,
DataReturn DATE,
nrCopiiDeCarti int
)


create table CategoriiDeVarsta(
IdCategoriiDeVarsta int primary key,
IntervalVarsta varchar(100)
)

create table CititoriCartiImprumutate(
IdCarteImprum int foreign key references CartiImprumutate(IdCarteImprum),
IdCititor int foreign key references Cititori(IdCititor), 
NrCartiImprumutate int,
constraint pk_CititoriCartiImprumutate primary key (IdCarteImprum, IdCititor)
)

create table CititoriCategoriiDeVarsta(
IdCategoriiDeVarsta int foreign key references CategoriiDeVarsta(IdCategoriiDeVarsta),
IdCititor int foreign key references Cititori(IdCititor), 
constraint pk_CititoriCategoriiDeVarsta primary key (IdCategoriiDeVarsta, IdCititor)
)

create table GenulCartiiImprumutate(
IdCarteImprum int foreign key references CartiImprumutate(IdCarteImprum),
IdGen int foreign key references GenulCartii(IdGen)
constraint pk_GenulCartiiImprumutate primary key (IdCarteImprum, IdGen)
)

------------------------------------COLOANE----------------------------------

insert into GenulCartii(Gen, Descriere, NrCartiCuAcelasiGen) values ('Actiune', 'Descriere1','48')
insert into GenulCartii(Gen, Descriere, NrCartiCuAcelasiGen) values ('Dragoste', 'Descriere2', '79')
insert into GenulCartii(Gen, Descriere, NrCartiCuAcelasiGen) values ('Fictiune', 'Descriere3', '65')
insert into GenulCartii(Gen, Descriere, NrCartiCuAcelasiGen) values ('Mister', 'Descriere4', '34')
insert into GenulCartii(Gen, Descriere, NrCartiCuAcelasiGen) values ('Istorie', 'Descriere5', '21')

insert into Carti(Titlu, Autor, NrSerie, NrPagini, IdGen) values ('Fata din tren', 'Paula Hawkins', 15475, 256, 1)
insert into Carti(Titlu, Autor, NrSerie, NrPagini, IdGen) values ('Ugly Love', 'Colleen Hoover', 25175, 300, 2)
insert into Carti(Titlu, Autor, NrSerie, NrPagini, IdGen) values ('Fluturi', 'Irina Binder', 22234, 160, 3)
insert into Carti(Titlu, Autor, NrSerie, NrPagini, IdGen) values ('Hotul de carti', 'Markus Zusak', 11278, 270, 4)
insert into Carti(Titlu, Autor, NrSerie, NrPagini, IdGen) values ('Secretele succesului', 'Dale Carnegie', 13368, 216, 5)

insert into Biblioteci(AdresaB, OrarBiblioteca, NrCititori) values ('Centru', '07:00 - 20:30', 56)
insert into Biblioteci(AdresaB, OrarBiblioteca, NrCititori) values ('Iris', '09:30 - 21:00', 25)
insert into Biblioteci(AdresaB, OrarBiblioteca, NrCititori) values ('Marasti', '10:00- 22:00', 214)
insert into Biblioteci(AdresaB, OrarBiblioteca, NrCititori) values ('Manastur', '08:30 - 20:30', 168)
insert into Biblioteci(AdresaB, OrarBiblioteca, NrCititori) values ('Gheorgheni', '09:30 - 21:00', 132)


insert into CartiBiblioteci(IdBiblioteca, IdCarte) values (1, 4), (1, 5), (2, 3), (4, 5), (3, 1)

insert into AdministratorBiblioteca(IdAdmin, Nume, Prenume, Experienta) values (1, 'Pop', 'Maria', 15)
insert into AdministratorBiblioteca(IdAdmin, Nume, Prenume, Experienta) values (2, 'Suciu', 'Denisa', 20)
insert into AdministratorBiblioteca(IdAdmin, Nume, Prenume, Experienta) values (3, 'Muresan', 'Ovidiu', 9)
insert into AdministratorBiblioteca(IdAdmin, Nume, Prenume, Experienta) values (4, 'Popescu', 'Andrei', 11)
insert into AdministratorBiblioteca(IdAdmin, Nume, Prenume, Experienta) values (5, 'Rusu', 'Alexandra', 5)


insert into Cititori(Nume, Prenume, Varsta, NrDeTelefon, Email) values ('Rusu', 'Flavia', 19, 0754781474, 'rusu@mail.com'), 
('Stan', 'Miruna', 15, 0721658984, 'stan@mail.com'), ('Matei', 'Raluca', 10, 0736547256, 'matei@mail.com'), 
('Cristea', 'Andrei', 21, 0747986451, 'cristea@mail.com'),
('Popa', 'Cristian', 25, 0754899657, 'popa@mail.com')


insert into CartiImprumutate(DataImprumut, DataReturn, nrCopiiDeCarti) values ('2022-03-20', '2022-04-01', 12)
insert into CartiImprumutate(DataImprumut, DataReturn, nrCopiiDeCarti) values ('2022-05-15', '2022-05-19', 10), ('2022-10-02', '2022-10-22', 3), 
('2022-07-16', '2022-08-08', 5), ('2022-01-29', '2022-02-07', 16)


insert into CategoriiDeVarsta(IdCategoriiDeVarsta,IntervalVarsta) values (1, 'Copii: 5-12 ani'), (2, 'Adolescenti: 14-18 ani'), 
(3, 'Studenti: 18-22'), (4, 'Adulti: 24-50 ani')


insert into CititoriCartiImprumutate(IdCarteImprum, IdCititor, NrCartiImprumutate) values (1, 2, 4), (2, 5, 2), (3, 4, 3), 
(4, 5, 2), (5, 1, 1), (2, 4, 4), (3, 1, 2), (1, 5, 7), (1, 3, 8), (4, 2, 6)

insert into CititoriCategoriiDeVarsta(IdCategoriiDeVarsta, IdCititor) values (1, 1)
insert into CititoriCategoriiDeVarsta(IdCategoriiDeVarsta, IdCititor) values (1, 5), (2, 4), (2, 3), (3, 1), (4, 2)


insert into GenulCartiiImprumutate(IdCarteImprum, IdGen) values (1, 4), (2, 2), (3, 2), (4, 1), (5, 3)




insert into CartiImprumutate(DataImprumut, DataReturn, nrCopiiDeCarti) values ('2023-01-13', '2023-01-13', 12)