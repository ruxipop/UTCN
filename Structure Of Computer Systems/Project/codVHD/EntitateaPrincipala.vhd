library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

entity EntitateaPrincipala is
   port (

      clk         : in     std_logic;
      rst         : in     std_logic;
      
      -- Periferice      
      btn         : in     std_logic;
      leds        : out    std_logic_vector(15 downto 0);
      
      -- UART
      uartData     : out    std_logic;
      pmodbt_rst   : out    std_logic;
      pmodbt_cts   : out    std_logic;
      
      -- Semnale microfon PDM
      pdm_m_clk    : out   std_logic; -- Output M_CLK signal to the microphone
      pdm_m_data   : in    std_logic; -- Input PDM data from the microphone
      pdm_lrsel    : out   std_logic; -- Set to '0', therefore data is read on the positive edge
     
      -- Semnale de audio output (folosite la serializator
      --pwm_audio : inout std_logic; -- Output Audio data to the lowpass filters
      --pwm_en    : out   std_logic; -- Output Audio enable

      -- Interfata DDR2
      ddr2_addr     : out   std_logic_vector(12 downto 0);
      ddr2_ba       : out   std_logic_vector(2 downto 0);
      ddr2_ras_n    : out   std_logic;
      ddr2_cas_n    : out   std_logic;
      ddr2_we_n     : out   std_logic;
      ddr2_ck_p     : out   std_logic_vector(0 downto 0);
      ddr2_ck_n     : out   std_logic_vector(0 downto 0);
      ddr2_cke      : out   std_logic_vector(0 downto 0);
      ddr2_cs_n     : out   std_logic_vector(0 downto 0);
      ddr2_dm       : out   std_logic_vector(1 downto 0);
      ddr2_odt      : out   std_logic_vector(0 downto 0);
      ddr2_dq       : inout std_logic_vector(15 downto 0);
      ddr2_dqs_p    : inout std_logic_vector(1 downto 0);
      ddr2_dqs_n    : inout std_logic_vector(1 downto 0)

   );
end EntitateaPrincipala;

architecture Behavioral of EntitateaPrincipala is

-- Amplificator de Clk
component clk_wiz_0
port
 ( 
  clk_in1 : in  std_logic; -- Intrare Clk de 100 MHz 
  clk_200 : out std_logic; -- Iesire Clk de 200 MHz
  -- Semnale de control
  reset   : in  std_logic;
  locked  : out std_logic
 );
end component;

-- Semnalele componentei clk_wiz_0 
signal reset          : std_logic;
signal locked         : std_logic;
signal clk_100MHz_buf : std_logic;
signal clk_200MHz_buf : std_logic;

-- Constante
constant SECONDS_TO_RECORD : integer := 5;
constant PDM_FREQ_HZ       : integer := 2_500_000;
constant SYS_CLK_FREQ_MHZ  : integer := 100_000_000;
constant NR_OF_BITS        : integer := 16;
constant SAMPLES_5_SEC     : integer := (((SECONDS_TO_RECORD * PDM_FREQ_HZ) / NR_OF_BITS) - 1);
constant RW_CYCLE_NS       : integer := 1200;


-- Stari
type state_type  is (Idle, Rec, Inter, Play);
type state_type2 is (RST_REG, LD_INIT_STR, SEND_CHAR, RDY_LOW, WAIT_RDY, WAIT_BTN, LD_BTN_STR);

-- Semnale
signal state     : state_type;
signal uartState : state_type2;
signal btnu_int         : std_logic;
signal btn2_int         : std_logic;
signal btnReg           : std_logic;
signal btnDetect        : std_logic;
signal rnw_int          : std_logic;
signal addr_int         : std_logic_vector(31 downto 0);
signal done_int         : std_logic;
signal pwm_audio_o_int  : std_logic; 
signal clk_i            : std_logic; 
-- Recording
signal pdm_clk_rising   : std_logic;
signal en_des           : std_logic;
signal done_des         : std_logic;
signal done_async_des   : std_logic;
signal data_des         : std_logic_vector(15 downto 0) := (others => '0');
signal data_dess        : std_logic_vector(31 downto 0) := (others => '0');
signal addr_rec         : std_logic_vector(31 downto 0) := (others => '0');
signal rSamplesCnt      : integer := 0;
signal done_des_dly     : std_logic;
-- Play
signal en_ser           : std_logic;
signal done_ser         : std_logic;
signal rd_ack_int       : std_logic;
signal data_ser         : std_logic_vector(31 downto 0);
signal data_serr        : std_logic_vector(15 downto 0);
signal done_async_ser   : std_logic;
signal addr_play        : std_logic_vector(31 downto 0) := (others => '0');
signal pSamplesCnt      : integer := 0;
signal done_ser_dly     : std_logic;
-- Memory
signal mem_a            : std_logic_vector(26 downto 0);
signal mem_a_int        : std_logic_vector(26 downto 0);
signal mem_dq_i         : std_logic_vector(15 downto 0);
signal mem_dq_o         : std_logic_vector(15 downto 0);
signal mem_cen          : std_logic;
signal mem_oen          : std_logic;
signal mem_wen          : std_logic;
signal mem_ub           : std_logic;
signal mem_lb           : std_logic;
signal mem_adv          : std_logic := '0';
signal mem_clk          : std_logic := '0';
signal mem_cre          : std_logic := '0';
-- UART
signal half_data        : std_logic_vector(7 downto 0) := (others =>'0');
signal half             : std_logic := '0';
signal start_uart       : std_logic := '0';
signal TxRdy            : std_logic := '0';
signal data_To_Send     : std_logic_vector(7 downto 0); 

begin
     
    -- Configurare Bluetooth    
    pmodbt_rst <= '1'; 
    pmodbt_cts <= '0';
     
    -- Semnalul de reset
    reset <= rst or (not locked);
   
    -- Amplificator de Clk
    Inst_ClkGen: clk_wiz_0
      port map(
         clk_in1 => clk,
         clk_200 => clk_200MHz_buf,  
         reset   => rst,
         locked  => locked);

   -- Debouncer
   Btnu: entity work.Dbncr
     generic map(
        NR_OF_CLKS => 4095)
     port map(
        clk => clk_i,
        button => btn,
        result => btnu_int);
      
   -- Deserializator
   Deserializer: entity WORK.Deserializer
     generic map(
        NbBits          => NR_OF_BITS,
        FREQ_IO_CLK_MHZ => SYS_CLK_FREQ_MHZ,
        FREQ_PDM_CLK_HZ => PDM_FREQ_HZ)
     port map(
        clk          => clk_i,
        en           => en_des,
        done         => done_async_des,
        data_output  => data_des,
        pdm_m_clk_o  => pdm_m_clk,
        pdm_m_data_i => pdm_m_data,
        pdm_lrsel_o  => pdm_lrsel);

   -- Controler de Memorie  
   RAM: entity work.RamCntrl
     generic map(
        RW_CYCLE_NS => RW_CYCLE_NS)
     port map(
        Clk          => clk_i,
        Rst          => rst, 
        RW_en        => rnw_int,
        Addr         => addr_int, 
        Data         => data_dess, 
        Data_output  => data_ser,
        CS           => done_int,
        rd_ack       => rd_ack_int,
        wr_ack       => open, 
        Byte         => "0011", 
        -- Semnale de memorie RAM
        MEM_DQ_I     => mem_dq_o,
        MEM_WAIT     => '0',
        MEM_A        => mem_a,
        MEM_DQ_O     => mem_dq_i, 
        MEM_DQ_T     => open,
        MEM_CEN      => mem_cen, 
        MEM_OEN      => mem_oen, 
        MEM_WEN      => mem_wen, 
        MEM_UB       => mem_ub, 
        MEM_LB       => mem_lb, 
        MEM_ADV      => mem_adv,
        MEM_CLK      => mem_clk,
        MEM_CRE      => mem_cre);
    
   -- Componenta Ram2DDR
   DDR: entity work.Ram2Ddr
     port map(
        clk_200MHz_i => clk_200MHz_buf,
        rst_i        => rst,
        ui_clk_o     => clk_i,
        -- Interfata RAM
        ram_a        => mem_a,
        ram_dq_i     => mem_dq_i,
        ram_dq_o     => mem_dq_o,
        ram_cen      => mem_cen,
        ram_oen      => mem_oen,
        ram_wen      => mem_wen,
        ram_ub       => mem_ub,
        ram_lb       => mem_lb,
        -- Interfata DDR2
        ddr2_addr    => ddr2_addr,
        ddr2_ba      => ddr2_ba,
        ddr2_ras_n   => ddr2_ras_n,
        ddr2_cas_n   => ddr2_cas_n,
        ddr2_we_n    => ddr2_we_n,
        ddr2_ck_p    => ddr2_ck_p,
        ddr2_ck_n    => ddr2_ck_n,
        ddr2_cke     => ddr2_cke,
        ddr2_cs_n    => ddr2_cs_n,
        ddr2_dm      => ddr2_dm,
        ddr2_odt     => ddr2_odt,
        ddr2_dq      => ddr2_dq,
        ddr2_dqs_p   => ddr2_dqs_p,
        ddr2_dqs_n   => ddr2_dqs_n);
      
    done_int <= done_des when state = Rec else
                done_ser when state = Play else '0';
    
    -- Genereaza semnalele de Done pentru deserializator si serializator  
    process(clk_i)
    begin
       if rising_edge(clk_i) then
          if rd_ack_int = '1' then
             data_serr <= data_ser(15 downto 0);
          end if;
          done_des <= done_async_des;
          data_dess <= x"0000" & data_des;
          done_ser <= done_async_ser;
       end if;
   end process;
   
   -- Serializator
--   Serializer: entity WORK.Serializer
--      generic map(
--         NbBits          => NR_OF_BITS,
--         FREQ_IO_CLK_MHZ => SYS_CLK_FREQ_MHZ,
--         FREQ_PDM_CLK_HZ => PDM_FREQ_HZ)
--      port map(
--         pdm_audio_in  => data_serr,
--         clk           => clk_i,
--         en            => en_ser, 
--         pwm_audio_out => pwm_audio,
--         done          => done_async_ser);
   
   -- Numara esantioanele si le stocheaza in memorie
   process(clk_i)
   begin
      if rising_edge(clk_i) then
         if state = Rec then
            if done_des = '1' then
               rSamplesCnt <= rSamplesCnt + 1;
            end if;
            if done_des_dly = '1' then
               addr_rec <= addr_rec + "10";
            end if;
         elsif state = Play then
           if done_ser = '1' then
              half <= not half;              
           end if;
           if done_ser_dly = '1' then
             if half='0' then
                pSamplesCnt <= pSamplesCnt + 1;
                addr_play <= addr_play + "10";
             end if;
           end if;
         else
            pSamplesCnt <= 0;
            addr_play <= (others => '0');        
            rSamplesCnt <= 0;
            addr_rec <= (others => '0');
         end if;
         done_des_dly <= done_des;
         done_ser_dly <= done_ser;
      end if;
   end process;
   
   rnw_int      <= '1' when state = Play else '0';
   en_ser       <= '1' when state = Play else '0';
   en_des       <= '1' when state = Rec else '0';
   addr_int     <= addr_rec when state = Rec else
                   addr_play when state = Play else
                   (others => '0');
   data_to_send <= data_ser(15 downto 8) when  (state=Play and half = '0') else
                   data_ser(7 downto 0) when (state=Play and half = '1') else
                   (others => '0');
             
   start_uart   <= '0' when state = Idle or (state = Play and pSamplesCnt = SAMPLES_5_SEC ) else
                     TxRdy when (state = Play and pSamplesCnt /= SAMPLES_5_SEC);    

   --pwm_en       <= '1'; 
   leds         <= (others => '1') when state = Rec else 
                     "1000000000000001" when state = Play else
                     (others => '0');  
 
   -- FSM
   NEXT_STATE_DECODE: process(clk_i)
   begin
      if rising_edge(clk_i) then
            if rst = '1' then
               state <= Idle;
            else 
                case (state) is
                    when Idle =>
                        if btnu_int = '1' then
                            state <= Rec;
                        end if;
                    when Rec =>
                        if rSamplesCnt = SAMPLES_5_SEC then
                            state <= Inter;
                        end if;
                    when Inter =>
                            state <= Play;
                    when Play =>
                        if btnu_int = '1' then
                            state <= Idle;
                        elsif pSamplesCnt = SAMPLES_5_SEC then
                            state <= Idle;
                        end if;
                    when others =>
                        state <= Idle;
                end case;
            end if;
        end if;   
    end process;
    
    
   -- UART
   uart_TX: entity WORK.UART_tx port map (TxData => data_to_send, Clk => clk_i ,Start => start_uart , Rst => rst, Tx=>uartData, TxRdy => TxRdy, Done => done_async_ser);

end Behavioral;