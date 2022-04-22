----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 27.02.2021 12:51:11
-- Design Name: 
-- Module Name: test_new - Behavioral
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



library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity test_new is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end test_new;

architecture Behavioral of test_new is
 component MPG
  Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           enable : out STD_LOGIC);
 end component;
signal enable : std_logic;
 signal count : std_logic_vector(2 downto 0); 
begin
  monopulse: MPG port map(btn(0),clk,enable);

 
 process(clk)
  begin
  if rising_edge(clk) then
   if enable = '1' then
   if sw(0) = '0' then
     count <= count + 1;
   else
     count <= count - 1;
   end if;
   end if;
  end if;   
  end process;
 
 

 process(count)
 begin
   case count is
     when "000" => led <= "0000000000000001";
     when "001" => led <= "0000000000000010";
     when "010" => led <= "0000000000000100";
     when "011" => led <= "0000000000001000";
     when "100" => led <= "0000000000010000";
     when "101" => led <= "0000000000100000";
     when "110" => led <= "0000000001000000";
     when "111" => led <= "0000000010000000";
    end case;
  end process;

end Behavioral;
