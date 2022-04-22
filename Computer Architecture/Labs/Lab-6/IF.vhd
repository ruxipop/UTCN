----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 22.03.2021 19:16:59
-- Design Name: 
-- Module Name: IF - Behavioral
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

entity IF_instr is
 Port (en:in STD_LOGIC;
       rst :in STD_LOGIC;
       clk : in STD_LOGIC;
       PCSrc: in STD_LOGIC;
       Jump: in STD_LOGIC;
       JumpAddress: in STD_LOGIC_VECTOR(15 downto 0);
       BranchAddress: in STD_LOGIC_VECTOR(15 downto 0);
       instr: out  STD_LOGIC_VECTOR(15 downto 0);
       instr_next:out STD_LOGIC_VECTOR(15 downto 0));
end IF_instr;

architecture Behavioral of IF_instr is
type tRom is array(0 to 32768) OF STD_LOGIC_VECTOR (15 downto 0);
         signal rom:tRom:=(
                                                B"010_000_001_0000000" , --X"4080" , addi $1, $0,0  initializeaza R1 cu 0
                                                B"010_000_010_0000100" ,-- X"4104" ,addi $2, $0,4 initializeaza R2 cu 4
                                                  B"000_000_000_011_0_000", --X"0030" ,add $3,$0,$0 initializeaza R3 CU 0
                                                  B"010_000_100_0000001",--X"4201" ,addi $4,$0,1 initializeaza R4 cu 1
                                                  B"010_000_101_0001100", --X"428C" ,addi $5,$0,12 initializeaza R5 cu 12
                                                  B"010_000_110_0000001",--X"4301" ,addi $6,$0,1 initializeaza R6 cu 1
                                                  B"101_001_101_0001000" ,--X"A688" ,beq $1,$5,8  DACA R1=R5 SARE cu 9 instr de la poz curemta
                                                  B"100_011_001_0000000",--X"8C80" ,sw $1,0($3)  stocheaza in memorie la adresa 0+R3 valoarea din R1
                                                  B"100_100_010_0000000",--X"9100" ,sw $2,0($4)  stocheaza in memorie la adresa 0+R4 valoarea din R2
                                                  B"011_011_001_0000000",--X"6C80", lw $1,0($3)  incarca din memorie inR1 de la adresa  0+R3
                                                  B"011_100_010_0000000",--X"7100", lw $2,0($4)  incarca din memorie inR2 de la adresa  0+R4
                                                  B"010_000_001_0000001" ,-- X"6081" ,addi $1, $0,1  Incrementeaza R1 CU 1
                                                  B"000_010_001_010_0_000", --X"08A3" ,add $2,$2,$1 R2<-R2+R1
                                                  B"000_101_110_101_0_001",--X"1750" ,sub $5,$5,$6  R5<R5-R6 adica R5 va fi decrementat cu 1
                                                  B"111_0000000000110",--X"E006". ,j 6. salt la instructiunea cu index 06 (a 7 din program)

                                               
                          others=>X"0000" );
                          
     signal PC: STD_LOGIC_VECTOR(15 downto 0) :=(others=>'0');
     signal PC_next: STD_LOGIC_VECTOR(15 downto 0) :=(others=>'0');
     signal mux: STD_LOGIC_VECTOR(15 downto 0) :=(others=>'0');
begin
process(clk)
begin
if rising_edge(clk) then 
 if rst='1' then
 PC<=X"0000";
 elsif en='1'   then
  PC<=PC_next;
  end if;
  end process;
  instr<=rom(conv_integer(PC));
  instr_next<=PC+1;
  mux<=PC+1 when PCSrc='0' else BranchAddress;
  PC_next<=mux when Jump='0' else JumpAddress;
end Behavioral;
