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
    signal count: STD_LOGIC_VECTOR (3 downto 0):=x"0";
       signal nr1: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        signal nr2: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
        
          signal add: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
            signal sub: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
              signal lsl: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
                signal lsr: STD_LOGIC_VECTOR (15 downto 0):=x"0000";
      signal cnt: STD_LOGIC_VECTOR (1 downto 0):="00";
    signal dir: STD_LOGIC;
     signal enable: STD_LOGIC;
     signal enable_reg: STD_LOGIC;
     signal reset: STD_LOGIC;
     --pt memorie R0M
     type t_mem is array(0 to 255) OF STD_LOGIC_VECTOR (15 downto 0);
      signal mem:t_mem:=(x"0001",x"0005",x"000A",others=>x"0000");
     signal data_ROM :STD_LOGIC_VECTOR(15 downto 0);
    signal data_a :STD_LOGIC_VECTOR(15 downto 0);
    signal data2:STD_LOGIC_VECTOR(15 downto 0);
    
    
    ---Lab 4
   -- type tRom is array(0 to 32768) OF STD_LOGIC_VECTOR (15 downto 0);
      --   signal rom:tRom:=(
        --                                        B"010_000_001_0000000" , --X"4080" , addi $1, $0,0  initializeaza R1 cu 0
          --                                      B"010_000_010_0000100" ,-- X"4104" ,addi $2, $0,4 initializeaza R2 cu 4
            --                                      B"000_000_000_011_0_000", --X"0030" ,add $3,$0,$0 initializeaza R3 CU 0
              --                                    B"010_000_100_0000001",--X"6201" ,addi $4,$0,1 initializeaza R4 cu 1
                --                                  B"010_000_101_0001100", --X"428C" ,addi $5,$0,12 initializeaza R5 cu 12
                  --                                B"010_000_110_0000001",--X"6301" ,addi $6,$0,1 initializeaza R6 cu 1
                    --                              B"101_001_101_0001000" ,--X"A688" ,beq $1,$5,8  DACA R1=R5 SARE cu 9 instr de la poz curemta
                      --                            B"100_011_001_0000000",--X"8C80" ,sw $1,0($3)  stocheaza in memorie la adresa 0+R3 valoarea din R1
                        --                          B"100_100_010_0000000",--X"9100" ,sw $2,0($4)  stocheaza in memorie la adresa 0+R4 valoarea din R2
                          --                        B"011_011_001_0000000",--X"6C80", lw $1,0($3)  incarca din memorie inR1 de la adresa  0+R3
                            --                      B"011_100_010_0000000",--X"7100", lw $2,0($4)  incarca din memorie inR2 de la adresa  0+R4
                              --                    B"010_000_001_0000001" ,-- X"6081" ,addi $1, $0,1  Incrementeaza R1 CU 1
                                --                  B"000_010_001_010_0_000", --X"08A3" ,add $2,$2,$1 R2<-R2+R1
                                  --                B"000_101_110_101_0_001",--X"1750" ,sub $5,$5,$6  R5<R5-R6 adica R5 va fi decrementat cu 1
                                    --              B"111_0000000000110",--X"E006". ,j 6. salt la instructiunea cu index 06 (a 7 din program)

                                               
                          --others=>X"0000" );
                          
             --LAB 5
             signal Instr ,PCinc: STD_LOGIC_VECTOR(15 downto 0);
             signal digits:STD_LOGIC_VECTOR(15 downto 0);
             signal en,rst:STD_LOGIC;
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
       component reg_file is
       Port ( clk:in std_logic;
         ra1: in std_logic_vector(3 downto 0);
         ra2: in std_logic_vector(3 downto 0);
         wa: in std_logic_vector(3 downto 0);
         wd :in std_logic_vector(15 downto 0);
         wen : in std_logic;
         rd1: out std_logic_vector(15 downto 0);
         rd2 :out std_logic_vector(15 downto 0)
          );
    end component;
    
    component bram is
     Port (clk:in std_logic;
       we:in std_logic;
       en : in std_logic;
       addr:in std_logic_vector(3 downto 0);
       di : in std_logic_vector(15 downto 0);
       do: out std_logic_vector(15 downto 0)
        );
    end component;
    component IF_instr is
    Port (en:in STD_LOGIC;
       rst :in STD_LOGIC;
       clk : in STD_LOGIC;
       PCSrc: in STD_LOGIC;
       Jump: in STD_LOGIC;
       JumpAddress: in STD_LOGIC_VECTOR(15 downto 0);
       BranchAddress: in STD_LOGIC_VECTOR(15 downto 0);
       instr: out  STD_LOGIC_VECTOR(15 downto 0);
       instr_next:out STD_LOGIC_VECTOR(15 downto 0));
       end component;
begin
 
  
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
  
  ---Lab 4
   --m1: mpg PORT MAP (btn=>btn(0), clk=>clk,enable=>enable);
  --display:SSD port map(clk,data_a(15 downto 0),an,cat);
   --data_a<=rom(conv_integer(count));
    
    --Lab5
    m1: mpg PORT MAP (btn=>btn(0), clk=>clk,enable=>en);
    m2: mpg PORT MAP (btn=>btn(1), clk=>clk,enable=>rst);
    inst_IF: IF_instr PORT MAP(en,rst,clk,sw(1),sw(0),x"0000",x"0000",Instr,PCinc);
    
    --mux 
    with sw(7) select 
      digits<=PCinc when '1',
              Instr when '0',
              (others=>'X') when others;
      display: SSD PORT MAP (clk,digits,an,cat);
    
    
    
   --numarator principal 
   --dir<=sw(0);
  -- process (clk,reset)
    --begin
    --if reset='1' then
    --count<=(others=>'0');
     --elsif rising_edge(clk) then
      --if  enable='1' then
      
     -- if dir='0' then
       --         count <= count + 1;
         --  else
           --      count <= count - 1;
           --end if;
      --end if;
     --end if;
      --end process;
         
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
