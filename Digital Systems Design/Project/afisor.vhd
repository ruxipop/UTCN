-- Pop Ruxandra	Maria
-- Divizor anod

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use work.tipuri_package1.all;

-- Entitate
-- Intrari: led (32 biti), clock_anod (semnal de tact)
-- Iesiri: catod (8 biti), anod (4 biti)
entity divizor_anod is
	port( led: in std_logic_vector(31 downto 0);
	clock_anod: in std_logic;
	catod: out std_logic_vector(7 downto 0);
	anod: out std_logic_vector(3 downto 0));
end entity;

-- Arhitectura
architecture arhitectura_afisor of divizor_anod is
begin
	process(clock_anod) -- se foloseste semnalul de tact clock_anod la proces
	variable r: reg; -- variabila registru 	  
	variable i: integer := 0; -- contor pentru divizorul de frecventa
	variable numar: integer := 0;  -- contor care determina ce anod e activ in functie de clock_anod
	begin
		if (rising_edge(clock_anod)) then	 -- daca e pe front crescator
				if (i = 1024) then 	 -- verificam daca contorul este 1024
				i := 0;	  -- se reseteaza contorul
				r(3) := led(31 downto 24); 	  -- se retine in registru informatia de pe led
				r(2) := led(23 downto 16);
				r(1) := led(15 downto 8);
				r(0) := led(7 downto 0);	
				
				if(numar > 3) then	  -- daca contorul e mai mare ca 3
					numar := 0;	-- se reseteaza contorul
					end if;								   
				
				case(numar) is			-- evaluarea contorului	pentru asignarea iesirilor
					when 0 => anod <= "0111";
					catod <= r(0);
					when 1 => anod <= "1011";
					catod <= r(1);
					when 2 => anod <= "1101";
					catod <= r(2);
					when 3 => anod <= "1110";
					catod <= r(3);
					when others => null;
				end case;
				-- dupa verificare, incrementam contoarele
				numar := numar+1; 
				end if;
				i := i + 1;
		end if;
		end process;
	end architecture;
				
