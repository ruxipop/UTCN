library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

use IEEE.NUMERIC_STD.ALL;


entity EX is
  Port (RD1: in std_logic_vector(15 downto 0);
        RD2: in std_logic_vector(15 downto 0);
        ALUSrc: in std_logic;
        Ext_Imm: in std_logic_vector(15 downto 0);
        sa: in std_logic;
        func: in std_logic_vector(2 downto 0);
        ALUOp: in std_logic_vector(1 downto 0);
        PC_1: in std_logic_vector(15 downto 0);
        GE: out std_logic; -- BGEZ
        Zero: out std_logic; -- BEQ
        ALURes: out std_logic_vector(15 downto 0);
        BranchAddress: out std_logic_vector(15 downto 0));
end EX;

architecture Behavioral of EX is
signal ALUCtrl: std_logic_vector(2 downto 0) := (others => '0');
signal A: std_logic_vector(15 downto 0) := (others => '0');
signal B: std_logic_vector(15 downto 0) := (others => '0');
signal C: std_logic_vector(15 downto 0) := (others => '0');
begin
--ALU CONTROL
process(ALUOp, func)
begin
case ALUOp is
  when "00" =>
  case func is
    when "000" => ALUCtrl <= "000"; --adunare
    when "001" => ALUCtrl <= "001";-- sub
    when "010" => ALUCtrl <= "010";--sll
    when "011" => ALUCtrl <= "011";--srl
    when "100" => ALUCtrl <= "100";--and
    when "101" => ALUCtrl <= "101";--or
    when "110" => ALUCtrl <= "110";--xor
    when "111" => ALUCtrl <= "111";--slt
    when others => ALUCtrl <= (others => 'X');
  end case;
  when "01" => ALUCtrl <= "000"; --addi,lw,sw se face adunare
  when "10" => ALUCtrl <= "001";-- beq,bne,bgez se face scadere
  when others => ALUCtrl <= (others => 'X');
end case;
end process;

-- ALU

A <= RD1;
ALURes <= C;
MUX: B<=RD2 when ALUSrc='0'  else Ext_Imm;


process(A, B, ALUCtrl, sa)
begin
case ALUCtrl is
  when "000" => C <= A + B; --add
  when "001" => C <= A - B; --sub
  ---sll
when "010"=>
   case sa is
  when '0' => C<=B;
  when '1'=>C<=B(14 downto 0)&'0';
  when others => C <=x"0000";
end case;
  --srl
  when "011" =>
  case sa is
  when '0' => C<=B;
  when '1'=>C<='0'&B(15 downto 1);
  when others => C<=x"0000";
end case;
  when "100" => C <= A and B; --AND
  when "101" => C <= A or B;  --or
  when "110" => C <= A xor B; --xor
  when "111" => --slt <
     if signed(A)<signed(B) then C<=x"0001";
     else C<=x"0000";
     end if;
  when others => C <= (others => '0');
end case;
end process;


isZero: Zero<='1' when C=x"0000" else '0';
BGEZ: GE<= NOT(C(15));


BranchAddress <= Ext_Imm + PC_1;


end Behavioral;