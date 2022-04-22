library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity MainControl is
 Port (Instr1 :in STD_LOGIC_VECTOR(15 downto 13);
        RegDst :out STD_LOGIC;
         ExtOp :out STD_LOGIC;
         ALUSrc :out STD_LOGIC;
         Branch:out STD_LOGIC;
         Jump :out STD_LOGIC;
         ALUOp :out STD_LOGIC_VECTOR(1 downto 0);
           MemWrite :out STD_LOGIC;
             MemtoReg :out STD_LOGIC;
             RegWrite: out STD_LOGIC;
             BNE:out STD_LOGIC;
              BGEZ:out STD_LOGIC
        );
end MainControl;

architecture Behavioral of MainControl is
begin
process(Instr1)
begin
RegDst<='0';ExtOp<='0';ALUSrc<='0';Branch<='0';Jump<='0';ALUOp<="00";MemWrite<='0';MemtoReg<='0';RegWrite<='0';
BNE <='0';BGEZ<='0';

case Instr1 is
--de tip R
when "000" =>
RegDst<='1';
RegWrite<='1';

--addi
when "001" =>

ExtOp<='1';
ALUSrc<='1';
RegWrite<='1';
ALUOp<="01";

--lw
when "010"  =>

ExtOp<='1';
ALUSrc<='1';
MemtoReg<='1';
RegWrite<='1';
ALUOp<="01";

--sw
when "011" =>

ExtOp<='1';
ALUSrc<='1';
ALUOp<="01";
MemWrite<='1';

--beq
when "100" =>

ExtOp<='1';
Branch<='1';

ALUOp<="10";

--bne
when "101" =>
ExtOp<='1';

ALUOp<="10";
BNE <='1';

--bgez
when "110" =>

ExtOp<='1';

ALUOp<="10";
BGEZ <='1';

when "111" =>

Jump<='1';

 when others=> 
 RegDst<='0';ExtOp<='0';ALUSrc<='0';Branch<='0';Jump<='0';ALUOp<="00";MemWrite<='0';MemtoReg<='0';RegWrite<='0';
BNE <='0';BGEZ<='0';
end case;
end process;
end Behavioral;