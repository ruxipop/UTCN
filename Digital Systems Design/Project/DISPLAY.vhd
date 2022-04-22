-- Pop Ruxandra Maria
-- Display (7 segmente)

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;

-- Entitate
-- Intrare: data (4 biti)
-- Iesire: catod (8 biti)
entity display is
	port(data:in std_logic_vector (3 downto 0);
	   catod:out std_logic_vector(7 downto 0));
end entity ;  

--Arhitectura
architecture arh_display of display is 
signal segment: std_logic_vector(7 downto 0) := (others => '0'); -- semnal care retine catozii activi
begin
	process(data) -- se foloseste intrarea data in proces 	   
	begin 
			 case data is
							when "0000" => segment <= "00000011"; -- 0
							when "0001" => segment <= "10011111"; -- 1
							when "0010" => segment <= "00100101"; -- 2
							when "0011" => segment <= "00001101"; -- 3
							when "0100" => segment <= "10011001"; -- 4
							when "0101" => segment <= "01001001"; -- 5
							when "0110" => segment <= "01000001"; -- 6   
							when "0111" => segment <= "00011111"; -- 7
							when "1000" => segment <= "00000001"; -- 8
							when "1001" => segment <= "00011001"; -- 9
							when "1010" => segment <= "00010001"; -- A
							when "1011" => segment <= "11000001"; -- b
							when "1100" => segment <= "01100011"; -- c
							when "1101" => segment <= "10000101"; -- d
							when "1110" => segment <= "01100001"; -- E
							when "1111" => segment <= "01110001"; -- F	
							when others => segment <= "00000001"; -- stare necunoscuta
						end case;		
						catod <= segment; -- lui catod i se atribuie valoarea lui segment	
			end process;
end arh_display;
