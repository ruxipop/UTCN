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
    
      -- signal nr1: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        --signal nr2: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        
          --signal add: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
            --signal sub: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
              --signal lsl: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
                --signal lsr: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
      --signal cnt: STD_LOGIC_VECTOR (1 downto 0):="00";
    --signal dir: STD_LOGIC;
     --signal enable: STD_LOGIC;
     --signal enable_reg: STD_LOGIC;
     --signal reset: STD_LOGIC;
     --pt memorie R0M
     --type t_mem is array(0 to 255) OF STD_LOGIC_VECTOR (15 downto 0);
      --signal mem:t_mem:=(x"0001",x"0005",x"000A",others=>x"0000");
     --signal data_ROM :STD_LOGIC_VECTOR(15 downto 0);
    --signal data_a :STD_LOGIC_VECTOR(15 downto 0);
    --signal data2:STD_LOGIC_VECTOR(15 downto 0);
    
    
    ---Lab 4
    signal count: STD_LOGIC_VECTOR (3 downto 0):=x"0";
    signal data_a :STD_LOGIC_VECTOR(15 downto 0);
    type tRom is array(0 to 32768) OF STD_LOGIC_VECTOR (15 downto 0);
      signal rom:tRom:=(B"000_000_000_001_0_000",  -- x"0010",add $1, $0, $0 contor bucla ,initializeaza R1 cu 0
                        B"001_000_100_0000111", -- x"2207",add $4, $0, $7 nr iterati,initializeaza R4 cu 7
                        B"000_000_000_010_0_000", --x"0020" add $2, $0, $0 initializare index locatie memorie,initializeaza R2 cu 0
                        B"000_000_000_101_0_000", --x"0050" ,add $5, $0, $0 suma numere negative ,initializeaza R5 cu 0
                        B"110_001_100_0001000",  -- x"C608",beq $1,$4 ,8 sau facut 7 iterati? daca $1=$4, sare 8 instructiuni inainte,
                          B"010_010_011_0101000", -- X"49A8" ,lw $3,40($2)  in $3 se aduce elementul curent din sir
                        B"001_011_011_1111110",  --x"2DFE" ,addi $3,$3,-2
                        B"011_010_011_0101000", -- X"69A8",sw $3,40($2) salvarea noii din R3 valori la adresa 40+R2 
                        B"111_110_001_0000001", -- X"F881",bgez $3,1 se verifica daca este mai mare decat 0 ,daca da se sare o instructiune inainte
                        B"000_101_011_101_0_000",  -- X"15D0",add $5,$5,$3 - daca nu se aduna in $5
                        B"001_011_011_0000100", --  X"2D84",aici sare , addi $2,$2,4 indexul urm element din sir
                        B"001_001_001_0000001", --X"2481",addi $1,$1,1  actualizeaza contorul $1 creste cu 1
                        B"110_0000000000101",  --X"C005",j 5 salt la inceputul buclei
                       B"011_000_101_1010000", --X"62D0" sw $5,80($0) salvarea sumei in memorie  la adresa 80 din R5
                       others=>X"0000" ); 
                       
                       
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
       
       --component reg_file is
      -- Port ( clk:in std_logic;
      --   ra1: in std_logic_vector(3 downto 0);
      --   ra2: in std_logic_vector(3 downto 0);
       --  wa: in std_logic_vector(3 downto 0);
        -- wd :in std_logic_vector(15 downto 0);
        -- wen : in std_logic;
        -- rd1: out std_logic_vector(15 downto 0);
       --  rd2 :out std_logic_vector(15 downto 0)
       --   );
  --  end component;
    
  --  component bram is
   --  Port (clk:in std_logic;
      -- we:in std_logic;
      -- en : in std_logic;
      -- addr:in std_logic_vector(3 downto 0);
     --  di : in std_logic_vector(15 downto 0);
     --  do: out std_logic_vector(15 downto 0)
     --   );
  -- end component;
--begin
 
  
--ROM
  -- display:SSD port map(clk,data_ROM(15 downto 0),an,cat);
    --data_ROM<=mem(conv_integer(count));
    
  --Registru
  --MPG_enable_1: mpg PORT MAP(btn=>btn(1),clk=>clk,enable=>enable_reg);
  --MPG_enable_2: mpg PORT MAP(btn=>btn(2),clk=>clk,enable=>reset);
  --registru :reg_file PORT MAP(clk=>clk,ra1=>count,ra2=>count,wa=>count,wd=>data_a,wen=>enable_reg,rd1=>data_ROM,rd2=>data2);
   --display:SSD port map(clk,data_a(15 downto 0),an,cat);
   --data_a<=data_ROM+data2;
   
   --BRAM
   
  -- MPG_enable_1: mpg PORT MAP(btn=>btn(1),clk=>clk,enable=>enable_reg);
   --MPG_enable_2: mpg PORT MAP(btn=>btn(2),clk=>clk,enable=>reset);
   --BRAM_1 : bram PORT MAP (clk=>clk,we=>enable_reg,en=>'1',addr=>count,di=>data_a,do=>data_ROM);
   --data_a<=data_ROM(13 downto 0) & "00";
   --display:SSD port map(clk,data_a(15 downto 0),an,cat);
  
  --Lab4
   m1: mpg PORT MAP (btn=>btn(0), clk=>clk,enable=>enable);
  display:SSD port map(clk,data_a(15 downto 0),an,cat);
   data_a<=rom(conv_integer(count));
    
    
   --numarator principal 
   dir<=sw(0);
   process (clk,reset)
    begin
    if reset='1' then
    count<=(others=>'0');
     elsif rising_edge(clk) then
      if  enable='1' then
      
      if dir='0' then
                count <= count + 1;
           else
                 count <= count - 1;
           end if;
      end if;
     end if;
      end process;
         
    --led<=count; 
   
  --  process(clk)
    --  begin
      --if rising_edge(clk) then
       --if enable='1' then
         --cnt<=cnt+1;
         --end if;
         --end if;
         --end process;
         --nr1<="000000000000"&sw(3 downto 0);
         --nr2<="000000000000"&sw(7 downto 4);
         --add<=nr1+nr2;
         --sub<=nr1-nr2;
         --lsl<="000000"&sw(7 downto 0) &"00";
         --lsr<="0000000000"&sw(7 downto 2);
         --process(cnt,add,sub,lsl,lsr)
         --begin
          --case cnt is
          --when "00" =>count<=add;
          --when "01"=>count<=sub;
          --when "10"=>count<=lsl;
          --when "11"=>count<=lsr;
 
          --end case;
          --end process;
 --detector: led(7)<='1' when count<=x"0000" else '0';


end Behavioral;
