library ieee;
use ieee.std_logic_1164.all;

entity Dbncr is

  generic( 
     NR_OF_CLKS : integer := 4095 );    
  
  port(
      clk : in  std_logic;  -- input clock
      button : in  std_logic;  -- input signal to be debounced
      result : out std_logic); -- debounced signal
end Dbncr;

architecture logic of Dbncr is

signal flipflops   : std_logic_vector(1 DOWNTO 0); -- input flip flops
signal counter_set : std_logic;                    -- sync reset to zero
signal count       : integer range 0 to NR_OF_CLKS-1;
  
begin
   counter_set <= flipflops(0) xor flipflops(1);  --determine when to start/reset counter
   
   process(clk)
      begin
         if rising_edge (clk) then      
            flipflops(0) <= button;                        --store button value in 1st flipflop
            flipflops(1) <= flipflops(0);                  --store 1st flipflop value in 2nd flipflop
            if (counter_set = '1') then                    --reset counter because input is changing
               count <= 0;                                    --clear the counter
            elsif(count = NR_OF_CLKS-1) then --stable input time is met 
               Result<= flipflops(1);                            --output the stable value
            else                                   --stable input time is not yet 
               count <= count + 1;                        --increment counter
            end if;  
         end if;
   end process;
end logic;
