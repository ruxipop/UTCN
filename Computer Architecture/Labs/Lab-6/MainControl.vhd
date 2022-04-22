----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 30.03.2021 12:47:07
-- Design Name: 
-- Module Name: MainControl - Behavioral
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

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

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
             BNE:out STD_LOGIC
        );
end MainControl;

architecture Behavioral of MainControl is
begin
process(Instr1)
begin
RegDst<='0';ExtOp<='0';ALUSrc<='0';Branch<='0';Jump<='0';ALUOp<="00";MemWrite<='0';MemtoReg<='0';RegWrite<='0';
BNE <='0';

case Instr1(15 downto 13) is
when "000" =>
RegDst<='1';
ExtOp<='X';
RegWrite<='1';


when "001" =>

ALUSrc<='1';
RegWrite<='1';
ALUOp<="11";

when "010"  =>

ExtOp<='1';
ALUSrc<='1';
RegWrite<='1';
ALUOp<="01";

when "011" =>

ExtOp<='1';
ALUSrc<='1';
MemtoReg<='1';
RegWrite<='1';
ALUOp<="01";


when "100" =>
RegDst<='X';
ExtOp<='1';
ALUSrc<='1';
MemWrite<='1';
MemtoReg<='X';
ALUOp<="01";


when "101" =>
RegDst<='X';
ExtOp<='1';
Branch<='1';
MemtoReg<='X';
ALUOp<="10";

when "110" =>
RegDst<='X';
ExtOp<='1';
MemtoReg<='X';
ALUOp<="10";
BNE <='1';

when "111" =>
RegDst<='X';
ExtOp<='X';
ALUSrc<='X';
Branch<='X';
Jump<='1';
MemtoReg<='X';
ALUOp<="XX";

end case;
end process;
end Behavioral;
