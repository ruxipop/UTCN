----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 02/22/2021 03:44:17 PM
-- Design Name: 
-- Module Name: test_env - Behavioral
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

entity test_env is
    PORT( clk: in STD_LOGIC;
          btn: in STD_LOGIC_VECTOR(4 downto 0);
          sw: in STD_LOGIC_VECTOR (15 downto 0);
          led: out STD_LOGIC_VECTOR(15 downto 0);
          an: out STD_LOGIC_VECTOR(3 downto 0);
          cat: out STD_LOGIC_VECTOR(6 downto 0));
end test_env;
--architecture Behavioral of test_env is
--begin
--led<=sw;
--an<=btn(3 downto 0);
--cat<=(others=> '0');
--end Behavioral;

architecture Behavioral of test_env is
    signal count: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
    signal dir: STD_LOGIC;
     signal enable: STD_LOGIC;
    
    component MPG is
        Port (btn: in STD_LOGIC;
              clk: in STD_LOGIC;
              enable: out STD_LOGIC);
    end component;
    
begin
    monopulse: MPG PORT MAP (btn=>btn(0), clk=>clk,enable=>enable);

    process (clk)
    begin
      if rising_edge(clk) then
      if  enable='1' then
           if dir='0' then
                count <= count + 1;
           else
                 count <= count - 1;
           end if;
      end if;
      end if;
      end process;
         
    led<=count; 
    dir<=sw(0);

end Behavioral;

