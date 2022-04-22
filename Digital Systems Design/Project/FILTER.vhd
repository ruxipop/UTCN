-- Pop Ruxandra Maria
-- Dispozitiv de calcul al mediei

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.tipuri_package1.all;	

-- Entitate
-- Intrari: length(pe 3 biti),numar(8 biti),reset, data_clock
-- Iesiri: numar_out(pe 8 biti)	,media(pe 8 biti)
entity filter is
	port(
	numar:in std_logic_vector(7 downto 0);
	data_clock:in std_logic;
	length:in std_logic_vector(2 downto 0);
	reset: in std_logic;	   
	media:out std_logic_vector(7 downto 0);   
	numar_out:out std_logic_vector(7 downto 0));
end entity;						

-- Arhitectura
architecture media_arh of filter is
signal numere:memory:=(others=>(others=>'0'));		--am realizat un tip de date (gen memorie/matrice) pentru a putea retine numerele 
begin
	process(data_clock, length, reset) -- se folosesc intrarile data_clock, length si reset in proces
	variable aj:unsigned(11 downto 0):=(others=>'0'); --am decalarat o variabila ajutatoare care sa ne retine numarul pt al putea pune in matrice
	variable suma:sum;			   --variabele in care se calculeaza sumele
	variable suma2:sume;
	variable suma4:sume;	
	variable suma8:sume;
	variable suma16:unsigned(11 downto 0);
	begin
		if(rising_edge(data_clock) and length/="000") then
			if(reset='1') then							  --daca resetul este 1 se reseteaza filtrul / resetul este sincron
				numere<=(others=>(others=>'0'));
				media<=(others=>'0');
			else
				aj(7 downto 0):=unsigned(numar);
				aj(11 downto 8):=(others=>'0');
				numere(15 downto 1)<=numere(14 downto 0);
				numere(0)<=aj;		
		
medie2_nr:for i in 7 downto 0 loop
	suma2(i):=numere(i*2)+numere(i*2+1);		 --se calculeaza suma a 2 nr
end loop;
medie4_nr:for i in 3 downto 0 loop			  -- a 4 nr in functie de suma2
	suma4(i):=suma2(i*2)+suma2(i*2+1);
end loop;
media8_nr:for i in 1 downto 0 loop			 -- a 8 nr in functie de suma4
	suma8(i):=suma4(i*2)+suma4(i*2+1);
end loop;
suma16:=suma8(1)+suma8(0);					 --a 16 nr in functie de suma16


-- se fac deplasarie la stanga in functie de suma calculata	pentru a se realiza media
aj:=suma2(0) srl 1;
suma(0):=std_logic_vector(aj(7 downto 0));
aj:=suma4(0) srl 2;
suma(1):=std_logic_vector(aj(7 downto 0));
aj:=suma8(0) srl 3;
suma(2):=std_logic_vector(aj(7 downto 0));
aj:=suma16 srl 4;
suma(3):=std_logic_vector(aj(7 downto 0));

numar_out <= numar;
-- se alege care medie se doreste a se afisa
if(length="100") then
	media<=suma(0);
elsif (length="101") then
	media<=suma(1);
elsif(length="110") then
	media<=suma(2);
elsif(length="111") then 
	media<=suma(3);
else
	numar_out<=(others=>'0');
	media<=(others=>'0');
end if;
end if;	   
end if;
end process;
end architecture ;
