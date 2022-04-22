-----------Test Bench---------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity Serializer_tb is
-- Port ( );
end Serializer_tb;

architecture Behavioral of Serializer_tb is
--------------------------------------------------------
-- Input signals
--------------------------------------------------------
signal clk : std_logic := '0';
signal pdm_audio_in : std_logic_vector(15 downto 0) := (others => '0');
signal en : std_logic := '0';

--------------------------------------------------------
-- Output signals
--------------------------------------------------------
signal done : std_logic := '0';
signal pwm_audio_out : std_logic := '0';

--------------------------------------------------------
-- CLK value constant
--------------------------------------------------------
constant clk_period : TIME := 10 ns;

begin
--------------------------------------------------------
-- Port mapping
--------------------------------------------------------
DUT: entity WORK.Serializer
   generic map(FREQ_IO_CLK_HZ => 75,
     FREQ_PDM_CLK_HZ => 2 )
    port map( pdm_audio_in => pdm_audio_in,
     clk => clk,
	 en => en,	
	 pwm_audio_out => pwm_audio_out,
	 done => done);

gen_clock: process
begin
   clk <= '0';
   wait for (clk_period/2);
   clk <= '1';
   wait for (clk_period/2);
end process gen_clock;

test: process
begin
   pdm_audio_in <= "0101011011110111";
   en <= '1'; 
   wait for 6500 ns;
   pdm_audio_in <= "0001000010010101";
   wait for 6500 ns;
end process test;
end Behavioral;