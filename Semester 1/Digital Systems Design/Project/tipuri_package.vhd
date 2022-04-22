-- Pop Ruxandra Maria si Zelenszky Bianca
-- Pachet care contine tipurile care le utilizam in proiect

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

package tipuri_package1 is
	type memory is array (15 downto 0) of unsigned (11 downto 0);
	type sume is array (7 downto 0) of unsigned (11 downto 0);
	type sum is array (3 downto 0) of std_logic_vector(7 downto 0);	 
	type student_array is array (5 downto 0) of std_logic_vector (7 downto 0);		   
	type reg is array(0 to 3) of std_logic_vector(7 downto 0);				
	type data_1 is array (3 downto 0) of std_logic_vector(3 downto 0); 
	type ies_c is array (3 downto 0) of std_logic_vector(7 downto 0);
end package ;
