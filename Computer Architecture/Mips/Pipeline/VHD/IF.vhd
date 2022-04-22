library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


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
type tRom is array(0 to 22) OF STD_LOGIC_VECTOR (15 downto 0);
         signal rom:tRom:=(
                                          B"001_000_001_0000000" , --X"2080" , addi $1, $0,0  initializeaza R1 cu 0
                                                        B"001_000_010_0000100" ,-- X"2104" ,addi $2, $0,4 initializeaza R2 cu 4
                                                          B"000_000_000_011_0_000", --X"0030" ,add $3,$0,$0 initializeaza R3 CU 0
                                                          B"001_000_100_0000001",--X"2201" ,addi $4,$0,1 initializeaza R4 cu 1
                                                          B"001_000_101_0001100", --X"228C" ,addi $5,$0,12 initializeaza R5 cu 12
                                                        
                                                          B"001_000_110_0000001",--X"2301" ,addi $6,$0,1 initializeaza R6 cu 1
                                                        B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP  pentru  eliminarea hazardului de date

                                                          
                                                          B"100_001_101_0001110" ,--X"868E" ,beq $1,$5,E  DACA R1=R5 SARE peste 15 instructiuni de la pozitia curenta
                                                           B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP pentru  eliminarea hazardului de control

                                                             B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 pentru  eliminarea hazardului de control

                                                               B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP. pentru  eliminarea hazardului de control

                                                          B"011_011_001_0000000",--X"6C80" ,sw $1,0($3)  stocheaza in memorie la adresa 0+R3 valoarea din R1
                                                          
                                                          B"011_100_010_0000000",--X"7100" ,sw $2,0($4)  stocheaza in memorie la adresa 0+R4 valoarea din R2
                                                         
                                                          B"010_011_001_0000000",--X"4C80", lw $1,0($3)  incarca din memorie in R1 de la adresa  0+R3
                                                         
                                                         B"010_100_010_0000000",--X"5100", lw $2,0($4)  incarca din memorie in R2 de la adresa  0+R4
                                                       B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP pentru  eliminarea hazardului de date

                                                          B"000_001_110_001_0_000",-- X"0710" ,add $1, $1,$6  Incrementeaza R1 CU 1
                                                      B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP pentru  eliminarea hazardului de date

                                                      B"000_000_000_000_0_000",--X"0000" ,add $0,$0,$0 NoOP pentru  eliminarea hazardului de date
                                                          B"000_010_001_010_0_000", --X"08A0" ,add $2,$2,$1 R2<-R2+R1
                                                          B"000_101_110_101_0_001",--X"1751" ,sub $5,$5,$6  R5<R5-R6 adica R5 va fi decrementat cu 1
                                                          B"111_0000000000111",--X"E007". ,j7. salt la instructiunea cu index 07 (a 8 din program):la inceputul buclei
         B"000_000_000_000_0_000", --X"0000" ,add $0,$0,$0 NoOP pentru  eliminarea hazardului de control

                                                       
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
  end if;
  end process;
  instr<=rom(conv_integer(PC));
  instr_next<=PC+1;
  mux<=PC+1 when PCSrc='0' else BranchAddress;
  PC_next<=mux when Jump='0' else JumpAddress;
end Behavioral;