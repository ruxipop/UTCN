library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_arith.ALL;

entity Serializer is
  generic( 
     NbBits          : integer := 16; 
     FREQ_IO_CLK_MHZ : integer := 100_000_000;
     FREQ_PDM_CLK_HZ : integer := 2_500_000 );
  port( 
     pdm_audio_in  : in  std_logic_vector(NbBits - 1 downto 0);
     clk           : in  std_logic;
     en            : in  std_logic;
     pwm_audio_out : out std_logic;
     done          : out std_logic);
end Serializer;

architecture Behavioral of Serializer is
signal m_clk          : std_logic := '0';
signal clk_count      : integer := 0;
signal tmp            : std_logic_vector(NbBits-1 downto 0) := (others => '0');
signal bit_count      : integer := 0;
signal prev_bit_count : integer := 0;

begin

   -- generarea semnalului de tact cu aceeasi frecventa cu semnalul m_clk trimis microfonului
   PDM_CLK_GEN: process(clk)
   begin
      if rising_edge(clk) then
         if en = '0' then
            clk_count <= 0;
            m_clk <= '0';
         else 
            if clk_count = ((FREQ_IO_CLK_MHZ / FREQ_PDM_CLK_HZ) - 1) then
                m_clk <= '1';
                clk_count <= 0;
            else 
                clk_count <= clk_count + 1;
                m_clk <= '0';    
            end if;    
         end if;
      end if;
   end process PDM_CLK_GEN;

   SHIFT: process(clk) 
   begin
      if rising_edge(clk) then
         if en = '0' then
            bit_count <= 0;
         else    
            if m_clk = '1' then
               if bit_count = (NbBits-1) then
                  bit_count <= 0;
               else
                  bit_count <= bit_count + 1;
               end if;
               if bit_count = 0 then    
                  tmp <= pdm_audio_in;     
               else 
                  tmp <= tmp(NbBits-2 downto 0) & '0';			 
               end if;
            end if;
         end if;   
      end if;    
   end process SHIFT;

   -- output
   pwm_audio_out <= '0' when tmp(NbBits-1) = '0' else 'Z';
   done          <= '1' when m_clk = '1' and bit_count = (NbBits-1) else '0';

end Behavioral;