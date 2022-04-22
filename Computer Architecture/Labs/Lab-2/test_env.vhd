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

architecture Behavioral of test_env is
    signal count: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
       signal nr1: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        signal nr2: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        
          signal add: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
            signal sub: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
              signal lsl: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
                signal lsr: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
      signal cnt: STD_LOGIC_VECTOR (1 downto 0):="00";
   -- signal dir: STD_LOGIC;
     signal enable: STD_LOGIC;
    
    component mpg is
        Port (btn: in STD_LOGIC;
              clk: in STD_LOGIC;
              enable: out STD_LOGIC);
    end component;
    component SSD is
    Port(clk :in STD_LOGIC;
    digits:in STD_LOGIC_VECTOR(15 downto 0);
       an :out STD_LOGIC_VECTOR(3 downto 0 );
       cat:out STD_LOGIC_VECTOR(6 downto 0)
       
       
       );
       end component;
    
begin
    m1: mpg PORT MAP (btn=>btn(0), clk=>clk,enable=>enable);
    display:SSD port map(clk,count(15 downto 0),an,cat);
  --  process (clk)
    --begin
      --if rising_edge(clk) then
      --if  enable='1' then
      -- if dir='0' then
        --        count <= count + 1;
          -- else
            --     count <= count - 1;
           --end if;
      --end if;
      --end if;
      --end process;
         
    --led<=count; 
    --dir<=sw(0);
    process(clk)
      begin
      if rising_edge(clk) then
       if enable='1' then
         cnt<=cnt+1;
         end if;
         end if;
         end process;
         nr1<="000000000000"&sw(3 downto 0);
         nr2<="000000000000"&sw(7 downto 4);
         add<=nr1+nr2;
         sub<=nr1-nr2;
         lsl<="000000"&sw(7 downto 0) &"00";
         lsr<="0000000000"&sw(7 downto 2);
         process(cnt)
         begin
          case cnt is
          when "00" =>count<=add;
          when "01"=>count<=sub;
          when "10"=>count<=lsl;
          when "11"=>count<=lsr;
 
          end case;
          end process;
 detector: led(7)<='1' when count<=x"0000" else '0';


end Behavioral;
