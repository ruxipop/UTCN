library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;
entity RamCntrl is
   generic(
      -- read/write cycle (ns)
      RW_CYCLE_NS : integer := 100
   );
   port(
      -- Control interface
      Clk         : in  std_logic; -- 100 MHz system clock
      Rst         : in  std_logic; -- active high system reset
      Byte        : in  std_logic_vector(3 downto 0); -- byte enable
      Addr        : in  std_logic_vector(31 downto 0); -- address input
      Data        : in  std_logic_vector(31 downto 0); -- data input
      Data_output : out std_logic_vector(31 downto 0); -- data output
      CS          : in  std_logic; -- active high chip select
      
      rd_ack      : out std_logic; -- read acknowledge flag
      wr_ack      : out std_logic; -- write acknowledge flag
      RW_en       : in  std_logic; -- read/write

      -- RAM Memory signals
      MEM_DQ_I    : in  std_logic_vector(15 downto 0); -- Data In

      MEM_WAIT    : in std_logic;
      MEM_A       : out std_logic_vector(26 downto 0); -- Address
      
      MEM_DQ_O    : out std_logic_vector(15 downto 0); -- Data Out
      MEM_DQ_T    : out std_logic_vector(15 downto 0); -- Data Tristate Enable, used for a bidirectional data bus only
      MEM_CEN     : out std_logic; -- Chip Enable
      MEM_OEN     : out std_logic; -- Output Enable
      MEM_WEN     : out std_logic; -- Write Enable
      MEM_UB      : out std_logic; -- Upper Byte
      MEM_LB      : out std_logic; -- Lower Byte
      MEM_ADV     : out std_logic; 
      MEM_CLK     : out std_logic;
      MEM_CRE     : out std_logic);
end RamCntrl;

architecture Behavioral of RamCntrl is

-- State machine state names
type type_state is(Idle, AssertCen, AssertOenWen, Waitt, Deassert, SendData,
               Ack, Done);

-- State machine signals
signal state : type_state := Idle;
	
-- For a 32-bit access, two cycles are needed
signal need2Cycle      : std_logic := '0';
	
-- Memory LSB
signal AddrLsb      : std_logic;
	
-- Internal registerred IP2_Bus bus
signal DataRdInt    : std_logic_vector(31 downto 0);
	
-- Integer for the counter of the rd/wr cycle time
signal CntCycleTime : integer range 0 to 255;

begin
--unused signals
   MEM_ADV <= '0';
   MEM_CRE <= '0';
   MEM_CLK <= '0';

------------------------------------------------------------------------
-- State Machine
------------------------------------------------------------------------

   FSM: process(Clk)
   begin
   if rising_edge(Clk) then
     if(Rst='1') then
     state<=idle;
     need2Cycle <='0';
   else
      case state  is
         when Idle =>
            if CS = '1' then
               state  <= AssertCen;
            else
               state  <= Idle;
            end if;
         when AssertCen => state  <= AssertOenWen;
         when AssertOenWen => state  <= Waitt;
         when Waitt =>
            if CntCycleTime = ((RW_CYCLE_NS/10) - 2) then
               state  <= Deassert;
            else
               state  <= Waitt;
            end if;
         when Deassert => state  <= SendData;
         when SendData =>
            if need2Cycle  = '1' then
               state  <= AssertCen;
            else
               state  <= Ack;
            end if;
         when Ack => state  <= Done;
         when Done => state  <= Idle;
         when others => state  <= Idle;
      end case;
      end if;
      end if;
   end process FSM;

------------------------------------------------------------------------
-- Counter for the write/read cycle time
------------------------------------------------------------------------
   CYCLE_COUNTER: process(Clk)
   begin
      if rising_edge(Clk) then
         if state  = Waitt then
            CntCycleTime <= CntCycleTime + 1;
         else
            CntCycleTime <= 0;
         end if;
      end if;
   end process CYCLE_COUNTER;

------------------------------------------------------------------------
-- Assert signals
------------------------------------------------------------------------
MEM_CEN<='0' when state =AssertOenWen or state =Waitt or state =Deassert else '1';
MEM_OEN<='0' when ((state =Waitt or state =Deassert )and RW_en='1') else '1';
MEM_WEN<='0' when ((state =Waitt or state =Deassert )and RW_en='0') else '1';
MEM_A<=(Addr(26 downto 1) & AddrLsb) when (( state  = AssertOenWen or state  = Waitt or state  = Deassert) and Rst='0') else (others=>'0');

------------------------------------------------------------------------
-- Send data to AXI bus
--acknowlegement signals
------------------------------------------------------------------------
Data_output<=DataRdInt when State=Ack;
rd_ack<= '1' when State = Ack and need2Cycle  = '0' and RW_en='1' else '0'; --read
wr_ack<= '1' when State = Ack and need2Cycle  = '0' and RW_en='0' else '0';  --write

------------------------------------------------------------------------
-- When a 32-bit access mode has to be performed, assert the need2Cycle 
-- signal
need2Cycle <=not need2Cycle  when state =AssertCen and Byte="1111" and Rst='0' else '0';
------------------------------------------------------------------------
-- Data is 32 bits
------------------------------------------------------------------------
data32Bits: process(Clk)
begin
   if rising_edge(Clk) then
      if Rst='1' then
        AddrLsb<='0';
      elsif (state =AssertCen) then
      -- '0' lower addres ,'1' higher addres
         if Byte="1111" then AddrLsb<=not need2Cycle ;
          elsif Byte="1100" or Byte="1000" or Byte="0100" then AddrLsb<='1';
          elsif  Byte="0011" or Byte="0010" or Byte="0001" then AddrLsb<='0';
         end if;
      end if;
   end if;
end process data32Bits;


------------------------------------------------------------------------
-- Assign Mem_DQ_O and Mem_DQ_T
------------------------------------------------------------------------

WriteData:process(Clk)
begin  
      
      if rising_edge(Clk) then
           if (Rst='1') then
                MEM_DQ_O<=(others=>'0');
           elsif RW_en='0' and (state =AssertOenWen or state=Waitt or state=Deassert) then
                if Byte="1111" then
                   if need2Cycle ='1' then
                     MEM_DQ_O<=Data(15 downto 0);
                   else
                     MEM_DQ_O<=Data(31 downto 16); --higher
                   end if;
                  elsif Byte="1100" or Byte="1000" or Byte="0100" then  MEM_DQ_O<=Data(31 downto 16);
                  elsif  Byte="0011" or Byte="0010" or Byte="0001" then  MEM_DQ_O<=Data(15 downto 0);
         else
                MEM_DQ_O<=(others=>'0');           
      end if;
   end if;
 end if;
end process WriteData;

   MEM_DQ_T <= (others => '1') when RW_en = '1' else (others => '0');

------------------------------------------------------------------------
-- Read data from the memory
------------------------------------------------------------------------
   ReadData:process(Clk)
begin
  if rising_edge(Clk) then
     if Rst='1' then

       dataRdInt<=(others=>'0');
     elsif state=Deassert then
         if Byte="1111" then
            if need2Cycle  ='1' then
              dataRdInt(15 downto 0)<=MEM_DQ_I;
            else
              dataRdInt(31 downto 16)<=MEM_DQ_I;
            end if;
         elsif Byte = "0011" or Byte ="1100" then
             dataRdInt(15 downto 0)<=MEM_DQ_I;
             dataRdInt(31 downto 16)<=MEM_DQ_I;
        elsif Byte = "0100" or Byte ="0001" then
             dataRdInt(7 downto 0)<=MEM_DQ_I(7 downto 0);
             dataRdInt(15 downto 8)<=MEM_DQ_I(7 downto 0);
             dataRdInt(23 downto 16)<=MEM_DQ_I(7 downto 0);
             dataRdInt(31 downto 24)<=MEM_DQ_I(7 downto 0);
         elsif Byte = "1000" or Byte ="0010" then
             dataRdInt(7 downto 0)<=MEM_DQ_I(15 downto 8);
             dataRdInt(15 downto 8)<=MEM_DQ_I(15 downto 8);
             dataRdInt(23 downto 16)<=MEM_DQ_I(15 downto 8);
             dataRdInt(31 downto 24)<=MEM_DQ_I(15 downto 8);       
       end if;  
     end if; 
  end if;
end process ReadData;




------------------------------------------------------------------------
-- Assign UB, LB (used in 8-bit write mode)
------------------------------------------------------------------------
   
   asignare_LB_UB:process(Clk)
begin
   if rising_edge(Clk) then
      if  RW_en='0' and (State=AssertOenWen or State=Waitt or State=Deassert) then
           if Byte="1000" or  Byte="0010" then
             MEM_UB<='0';
             MEM_LB<='1';
           elsif Byte="0100" or Byte="0001" then
              MEM_UB<='1';
              MEM_LB<='0';
           else
                 MEM_UB<='0';
                 MEM_LB<='0';
           end if;
       else
       --cand se citeste
             MEM_UB<='0';
             MEM_LB<='0';  
      end if;  
   end if;
end process asignare_LB_UB;

end Behavioral;