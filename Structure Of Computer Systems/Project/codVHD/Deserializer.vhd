library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

entity Deserializer is
   generic(
      NbBits          : integer := 16; 
      FREQ_IO_CLK_MHZ : integer := 100_000_000;
      FREQ_PDM_CLK_HZ : integer := 2_500_000 );

   port(
      clk          : in  std_logic;
      en           : in  std_logic; -- semnalul care permite deserializarea
      done         : out std_logic; -- semnalizeaza ca 16 biti au fost deserializati
      pdm_m_data_i : in  std_logic; -- data primita de la microfon in format PDM
      data_output  : out std_logic_vector(NbBits - 1 downto 0); --data pe 16 biti care a fost    deserializata
      pdm_m_clk_o  : out std_logic; -- iesirea semnalului M_CLK catre microfon
      pdm_lrsel_o  : out std_logic -- datele sunt citite pe front crescator deci mereu va fi setat pe 0    
  );
end Deserializer;

architecture Behavioral of Deserializer is

signal cnt_clk            : integer := 0;
signal clk_int            : std_logic := '0';
signal m_clk              : std_logic;
signal pdm_data           : std_logic_vector((NbBits - 1) downto 0);
signal count_bits         : integer := 0; -- nr de biti citi /maxim 16
signal en_int             : std_logic;
signal pdm_clk_rising_reg : std_logic_vector (2 downto 0);
 
begin

   -- dupa cum am zis mai sus trebuie setat pe '0' pentru a simboliza o citire pe front crescator
   pdm_lrsel_o <= '0';


   -- generarea semnalului de tact cu aceeasi frecventa cu semnalul m_clk trimis microfonului
   PDM_GEN_CLK: process(clk)
      begin
         if rising_edge(clk) then
            if cnt_clk = ((FREQ_IO_CLK_MHZ/(FREQ_PDM_CLK_HZ*2))-1) then
               cnt_clk <= 0;
               clk_int <= not clk_int;
               if clk_int = '0' then
                  m_clk <= '1';
               end if;
            else
               cnt_clk <= cnt_clk + 1;
               m_clk <= '0';
            end if;
         end if;
   end process PDM_GEN_CLK;

  -- numaram nr de biti care au fost esantionati
  CNT_BITS: process(clk) begin
     if rising_edge(clk) then
        if en = '0' then
           count_bits <= 0;
        else
           if m_clk = '1' then
              if count_bits = (NbBits-1) then
                 count_bits <= 0;
              else
                 count_bits <= count_bits + 1;
              end if;
              pdm_data <= pdm_data(NbBIts-2 downto 0) & pdm_m_data_i;
           end if;
        end if;
     end if;
  end process CNT_BITS;

 -- verificam daca au fost esantionati toti cei 16 biti
 done        <= '1' when m_clk = '1' and en = '1' and count_bits = 0 else '0';
 data_output <= pdm_data when m_clk = '1' and en = '1' and count_bits = 0;
 pdm_m_clk_o <= clk_int;

end Behavioral;
