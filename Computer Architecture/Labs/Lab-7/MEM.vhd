
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity MEM is
 Port (MemWrite:in STD_LOGIC;
       ALUResIn:in std_logic_vector(15 DOWNTO 0);
       RD2:in std_logic_vector(15 DOWNTO 0);
       CLK:in STD_LOGIC;
       EN:in STD_LOGIC;
       MemData:out std_logic_vector(15 DOWNTO 0);
         ALUResOut:out std_logic_vector(15 DOWNTO 0));
end MEM;

architecture Behavioral of MEM is
type r_MEM is array(0 to 31) of STD_LOGIC_VECTOR(15 downto 0);
signal MEM :r_MEM :=( 
others=>"0000");

signal Address :std_logic_vector (4 downto 0):=(others=>'0');

begin
Address <= ALUResIn(4 downto 0);
process(CLK)
begin
if rising_edge(CLK) then
if EN='1' and MemWrite='1' then
  MEM(conv_integer(Address))<=RD2;
  end if;
  end if;
  end process;
  MemData<=MEM(conv_integer(Address));

end Behavioral;
