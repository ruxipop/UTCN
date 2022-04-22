-- Zelenszky Bianca
-- Generator

 library IEEE;
 use IEEE.std_logic_1164.all;
 use IEEE.numeric_std.all;
 use work.tipuri_package1.all;
 
 -- Entitate
-- Intrari: control (pe 3 biti), reset, clk
-- Iesiri: number (pe 8 biti) 
 entity generator is
	 port(clk, reset: in std_logic;
	 control: in std_logic_vector(2 downto 0);
	 number: out std_logic_vector(7 downto 0));
end generator;

-- Arhitectura
architecture arhitectura_generator of generator is
begin
	clock_proc: process(CLK)
	constant eticheta1:student_array:=("00000000", "00001001", "00001000", "00000001", "00000010", "00000101");-- secventa studentului 1:  0, 9, 8, 1, 2, 5
	constant eticheta2:student_array:=("00000001", "00000100", "00000110", "00001000", "00000011", "00000111");-- secventa studentului 2:  1, 4, 6, 8, 3, 7
	variable student1:student_array:=eticheta1;
	variable student2:student_array:=eticheta2;
	variable aleator_4: std_logic_vector(3 downto 0):=("0111"); -- secventa aleatoare pe 4 biti: 7, 15 14, 12, 8, 1, 2, 4, 9, 3, 6, 13, 10, 5, 11, 7... 
	variable aleator_8: std_logic_vector(7 downto 0):=("11111111");-- secventa aleatoare pe 8 biti
	variable aj: std_logic_vector(7 downto 0):=(others=>'0');-- variabila ajutatoare
	begin
		if clk'event and clk = '1' then	 -- numerele se genereaza pe front crescator
			if(reset = '1') then 	   -- reset sincron
				student1 := eticheta1;
				student2 := eticheta2;
			    aleator_8 := ("00000100");
				aleator_4 := ("0111");
				number <= (others => '0');
				 
			else
				case(control)is
					when "000"=> number<=(others=>'0');
					when "001"=> null;--square wave
					when "010"=>  -- secventa studentului 1
					aj:=student1(5);
					student1(5 downto 1):=student1(4 downto 0);	-- se deplaseaza spre stanga
					student1(0):=aj;   -- bitul nr 5 vine pe pozitia bitului nr 0
					number(5 downto 0)<= student1(0)(5 downto 0); -- se copiaza in primii 6 biti ai lui number numarul generat din secventa studentului 1
					number(7 downto 6)<=(others =>'0');	   -- nu avem nevoie de bitii 7 si 6, iau valoarea 0
					when "011"=>  -- secventa studentului 2
					aj:=student2(5);
					student2(5 downto 1):= student2(4 downto 0);    -- se deplaseaza spre stanga
					student2(0):=aj;    -- bitul nr 5 vine pe pozitia bitului nr 0
					number(5 downto 0)<= student2(0)(5 downto 0);	-- se copiaza in primii 6 biti ai lui number numarul generat din secventa studentului 2
					number(7 downto 6)<=(others=>'0'); 	 -- nu avem nevoie de bitii 7 si 6, iau valoarea 0
					when "110"=>	-- secventa de 4 nr aleatoare, aleator_4 are initial valoarea "0111"
					number(3 downto 0)<= aleator_4;		 
					number(7 downto 4)<=(others=>'0');	
					aj(0):= aleator_4(3) xor aleator_4(2);	  -- in bitul 0 din aux memoram rezultatul operatiei de xor intre bitul 2 si 3 al variabilei aleator_4, ca sa se genereze secventa aleatoare
					aleator_4(3 downto 1):=aleator_4(2 downto 0);  -- se deplaseaza bitii din aleator_4 spre stanga
					aleator_4(0):=aj(0);	  -- bitul 0 din aleator_4 i se da bitul calculat din aux
					when "111"=>		 -- secventa de 8 nr aleatoare, aleator_8 este initial "11111111"
					aj(0):= aleator_8(7) xor aleator_8(6);	-- in bitul 0 din aux memoram rezultatul operatiei de xor intre bitul 6 si 7 al variabilei aleator_8, ca sa se genereze secventa aleatoare
					aleator_8(7 downto 1):= aleator_8(6 downto 0);	  -- se deplaseaza bitii din aleator_8 spre stanga
					aleator_8(0):= aj(0);	   -- bitul 0 din aleator_8 i se da bitul calculat din aux
					number<= aleator_8;
					when others => number<=(others=>'0');  -- Pentru "000" si alte combinatii de butoane se face Test Mode
				end case;
			end if;
			end if;
		end process clock_proc;
		end arhitectura_generator;
					
