-- Zelenszky Bianca
-- Divizor de frecventa

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.tipuri_package1.all;  

-- Entitate
-- Intrari: clk, reset
-- Intrare/Iesire: Q care are valoarea initiala '0' de tip std_logic
entity divizor_generator is
	port( clk, reset :in std_logic;
	Q: inout std_logic:='0');
end;

-- Arhitectura
architecture frecventa of divizor_generator is
begin
	clock_proc: process(clk) -- se foloseste intrarea clk in proces
	variable i: unsigned (27 downto 0) := (others => '0');  -- variabila folosita ca si contor
	begin
		if (rising_edge(clk)) then  -- cand suntem pe front crescator
			if(reset= '1') then	  -- cand reset-ul este 1
			Q<=not(Q);	 -- se genereaza clk normal
		else	-- altfel
			i:= i+1;	 -- incrementam contorul
			if(i=60000000)then   -- verificam daca a ajuns la 24999999
				Q<=not(Q);	-- se modifica clk-ul
				i:=(others => '0');	  -- se reseteaza contorul
				end if;
			end if;
		end if;
	end process;
end architecture;
