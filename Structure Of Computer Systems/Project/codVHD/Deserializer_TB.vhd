-----------Test Bench---------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity Deserializer_tb is
-- Port ( );
end Deserializer_tb;

architecture Behavioral of Deserializer_tb is
--------------------------------------------------------
-- Input signals
--------------------------------------------------------
 signal clk : std_logic := '0';
 signal en : std_logic := '0';
 signal done : std_logic := '0';
 signal pdm_m_data_i : std_logic := '0';
--------------------------------------------------------
-- Output signals
--------------------------------------------------------
 signal data_output : std_logic_vector(15 downto 0) := (others => '0');
 signal pdm_m_clk_o : std_logic := '0';
 signal pdm_lrsel_o : std_logic := '0';
--------------------------------------------------------
-- CLK value constant
--------------------------------------------------------

 constant clk_period : TIME := 10 ns;
 
 begin
--------------------------------------------------------
-- Port mapping
--------------------------------------------------------
 DUT: entity WORK.Deserializer generic map(
  NbBits => 16,
  FREQ_IO_CLK_HZ => 75000000,
  FREQ_PDM_CLK_HZ => 2500000
 )
 port map(
  clk => clk,
  en => en,
  done => done,
  pdm_m_data_i => pdm_m_data_i,
  data_output => data_output,
  pdm_m_clk_o => pdm_m_clk_o,
  pdm_lrsel_o => pdm_lrsel_o
);
gen_clock: process
 begin
  clk <= '0';
  wait for (CLK_PERIOD/2);
  clk <= '1';
  wait for (CLK_PERIOD/2);
end process gen_clock;

test: process
 begin
  for i in 0 to 15 loop
   if i mod 2 = 0 then
    pdm_m_data_i <= '1';
   else
    pdm_m_data_i <= '0';
   end if;
   en <= '1';
   wait for 300 ns;
  end loop;
end process;

end Behavioral;