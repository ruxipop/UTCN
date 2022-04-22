
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity ID is
 Port (
 
   RegWrite : in STD_LOGIC;
   Instr :in STD_LOGIC_VECTOR(15 downto 0);
   RegDst :in STD_LOGIC;
   CLK: in STD_LOGIC;
   EN :in STD_LOGIC;
   ExtOp: in STD_LOGIC;
   WD: in STD_LOGIC_VECTOR(15 downto 0);
   RD1: out STD_LOGIC_VECTOR(15 downto 0);
    RD2: out STD_LOGIC_VECTOR(15 downto 0);
    Ext_imm : out STD_LOGIC_VECTOR (15 downto 0);
    func :out STD_LOGIC_VECTOR(2 downto 0);
    sa: out STD_LOGIC);
end ID;

architecture Behavioral of ID is
component reg_file is
  Port ( clk:in std_logic;
         ra1: in std_logic_vector(2 downto 0);
         ra2: in std_logic_vector(2 downto 0);
         wa: in std_logic_vector(2 downto 0);
         wd :in std_logic_vector(15 downto 0);
         wen : in std_logic;
         rd1: out std_logic_vector(15 downto 0);
         rd2 :out std_logic_vector(15 downto 0);
         RegWrite: in std_logic );
end component;
    signal mux : std_logic_vector(2 downto 0):=(others=>'0') ;
begin
 mux<=Instr(9 downto 7) when RegDst='0' else Instr(6 downto 4);
rf :reg_file port map (CLK,Instr(12 downto 10),Instr(9 downto 7),mux,WD,EN,RD1,RD2, RegWrite);
func<=Instr(2 downto 0);
sa<=Instr(3);

Ext_imm<=Instr(6)&Instr(6) & Instr(6)&Instr(6)&Instr(6)&Instr(6) &Instr(6) &Instr(6)&Instr(6)& Instr(6 downto 0) when ExtOp='1' else "000000000" & Instr(6 downto 0);

end Behavioral;