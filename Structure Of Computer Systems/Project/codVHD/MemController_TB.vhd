-----------Test Bench---------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity MemController_TB is
end MemController_TB;

architecture Behavioral of MemController_TB is
signal Clk         : std_logic := '0';
signal Rst         : std_logic := '0'; -- active high system reset
signal Byte        : std_logic_vector(3 downto 0) := (others => '0'); -- byte enable
signal Addr        : std_logic_vector(31 downto 0) := (others => '0'); -- address input
signal Data        : std_logic_vector(31 downto 0) := (others => '0'); -- data input
signal Data_output : std_logic_vector(31 downto 0) := (others => '0'); -- data output
signal CS          : std_logic := '0'; -- active high chip select   
signal rd_ack      : std_logic := '0'; -- read acknowledge flag
signal wr_ack      : std_logic := '0'; -- write acknowledge flag
signal RW_en       : std_logic := '0'; -- read/write
signal MEM_DQ_I    : std_logic_vector(15 downto 0) := (others => '0'); -- Data In
signal MEM_WAIT    : std_logic := '0';
signal MEM_A       : std_logic_vector(26 downto 0) := (others => '0'); -- Address  
signal MEM_DQ_O    : std_logic_vector(15 downto 0) := (others => '0'); -- Data Out
signal MEM_DQ_T    : std_logic_vector(15 downto 0) := (others => '0'); -- Data Tristate Enable, used for a bidirectional data bus only
signal MEM_CEN     : std_logic := '0'; -- Chip Enable
signal MEM_OEN     : std_logic := '0'; -- Output Enable
signal MEM_WEN     : std_logic := '0'; -- Write Enable
signal MEM_UB      : std_logic := '0'; -- Upper Byte
signal MEM_LB      : std_logic := '0'; -- Lower Byte
signal MEM_ADV     : std_logic := '0'; 
signal MEM_CLK     : std_logic := '0';
signal MEM_CRE     : std_logic := '0';
signal need2Cycle  : std_logic := '0';

constant CLK_PERIOD : TIME := 10 ns;

begin

    gen_clock: process
    begin
        Clk <= '0';
        wait for (CLK_PERIOD/2);
        Clk <= '1';
        wait for (CLK_PERIOD/2);
    end process gen_clock;    

    DUT : entity WORK.RamCntrl 
    generic map
    (
      RW_CYCLE_NS => 100
    )
     port map(
      Clk         => Clk,
      Rst         => Rst,
      Byte        => Byte,
      Addr        => Addr,
      Data        => Data,
      Data_output => Data_output,
      CS          => CS,
  
      rd_ack      => rd_ack,
      wr_ack      => wr_ack,
      RW_en       => RW_en,

      MEM_DQ_I    => MEM_DQ_I,
      MEM_WAIT    => MEM_WAIT,
      MEM_A       => MEM_A,
      MEM_DQ_O    => MEM_DQ_O,
      MEM_DQ_T    => MEM_DQ_T,
      MEM_CEN     => MEM_CEN,
      MEM_OEN     => MEM_OEN,
      MEM_WEN     => MEM_WEN,
      MEM_UB      => MEM_UB,
      MEM_LB      => MEM_LB,
      MEM_ADV     => MEM_ADV, 
      MEM_CLK     => MEM_CLK,
      MEM_CRE     => MEM_CRE);

test: process
variable cycle_time_count : integer := 0;

begin
    -- writing
    rw_en <= '0';
    
    -- idle -> idle
    Rst <= '1';
    wait for CLK_PERIOD;
    
    -- idle -> idle
    Rst <= '0';
    CS <= '0';
    wait for CLK_PERIOD;

    -- idle -> AssertCen
    CS <= '1';
    wait for CLK_PERIOD;
    
    -- AssertCen -> AssertOen
    wait for CLK_PERIOD;
    
    -- AssertOen -> Wait
    wait for CLK_PERIOD;
    for i in 0 to 7 loop
        cycle_time_count := cycle_time_count + 1;
        wait for CLK_PERIOD;
    end loop;
    
    -- Wait -> Deassert
    wait for CLK_PERIOD;
    
    -- Deassert -> SendData
    wait for CLK_PERIOD;
    
    -- SendData -> AssertCen
    need2Cycle <= '1';
    wait for CLK_PERIOD;
    
    -- AssertCen -> AssertOen
    wait for CLK_PERIOD;
    
    -- AssertOen -> Wait
    wait for CLK_PERIOD;
    for i in 0 to 7 loop
        cycle_time_count := cycle_time_count + 1;
        wait for CLK_PERIOD;
    end loop;
    
    -- Wait -> Deassert
    wait for CLK_PERIOD;
    
    -- Deassert -> SendData
    wait for CLK_PERIOD;
  
    -- SendData -> Ack  
    need2Cycle <= '0';
    wait for CLK_PERIOD;

    -- Ack -> Done
    wait for CLK_PERIOD;

    -- Done -> Idle
    wait for CLK_PERIOD;


    -- reading
    rw_en <= '1';

    -- idle -> idle
    Rst <= '1';
    wait for CLK_PERIOD;
   
    -- idle -> idle
    Rst <= '0';
    CS <= '0';
    wait for CLK_PERIOD;

    -- idle -> AssertCen
    CS <= '1';
    wait for CLK_PERIOD;
    
    -- AssertCen -> AssertOen
    wait for CLK_PERIOD;
    
    -- AssertOen -> Wait
    wait for CLK_PERIOD;

    for i in 0 to 7 loop
        cycle_time_count := cycle_time_count + 1;
        wait for CLK_PERIOD;
    end loop;
    
    -- Wait -> Deassert
    wait for CLK_PERIOD;

    -- Deassert -> SendData
    wait for CLK_PERIOD;

    -- SendData -> AssertCen
    need2Cycle <= '1';
    wait for CLK_PERIOD;

    -- AssertCen -> AssertOen
    wait for CLK_PERIOD;

    -- AssertOen -> Wait
    wait for CLK_PERIOD;

    for i in 0 to 7 loop
        cycle_time_count := cycle_time_count + 1;
        wait for CLK_PERIOD;
    end loop;

    -- Wait -> Deassert
    wait for CLK_PERIOD;

    -- Deassert -> SendData
    wait for CLK_PERIOD;

    -- SendData -> Ack
    need2Cycle <= '0';
    wait for CLK_PERIOD;
 
    -- Ack -> Done
    wait for CLK_PERIOD;

    -- Done -> Idle
    wait for CLK_PERIOD;

end process test;
end Behavioral;
