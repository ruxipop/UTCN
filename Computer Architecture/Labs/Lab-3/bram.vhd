----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 09.03.2021 15:45:22
-- Design Name: 
-- Module Name: ram_no_change - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;



entity bram is
 Port (clk:in std_logic;
       we:in std_logic;
       en : in std_logic;
       addr:in std_logic_vector(3 downto 0);
       di : in std_logic_vector(15 downto 0);
       do: out std_logic_vector(15 downto 0)
        );
end bram;

architecture Behavioral of bram is
  type ram_type is array(0 to 255) of std_logic_vector(15 downto 0);
  signal RAM:ram_type:=(x"0000",x"0001",x"0002",x"0003",x"0004",x"0005",x"0006",x"0007",others=>x"0000");
  
begin
process(clk)
begin
  if rising_edge(clk) then
  if en='1' then
  if we='1' then
  RAM(conv_integer(addr))<=di;
  do<=di;
  else
  do<=RAM(conv_integer(addr));
 end if;
 end if;
 end if;
 end process;

end Behavioral;
