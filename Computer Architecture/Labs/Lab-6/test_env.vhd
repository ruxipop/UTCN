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

    
            
             signal Instr ,PCinc: STD_LOGIC_VECTOR(15 downto 0);
             signal digits:STD_LOGIC_VECTOR(15 downto 0);
             signal en,rst,RegWrite,ExtOp,sa,ALUSrc,Branch,Jump,MemWrite,MemtoReg,BNE:STD_LOGIC;
                 signal RegDst:STD_LOGIC;
              signal sum:STD_LOGIC_VECTOR(15 downto 0);
              signal RD1,RD2,Ext_func,Ext_sa,Ext_imm:STD_LOGIC_VECTOR(15 downto 0);
              signal func:STD_LOGIC_VECTOR(2 downto 0);
              signal ALUOp:STD_LOGIC_VECTOR(1 downto 0);
             
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
         rd2 :out std_logic_vector(15 downto 0);
         RegWrite:in std_logic );
    end component;
    
    component bram is
     Port (clk:in std_logic;
       we:in std_logic;
       en : in std_logic;
       addr:in std_logic_vector(3 downto 0);
       di : in std_logic_vector(15 downto 0);
       do: out std_logic_vector(15 downto 0) );
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
       
   component ID is
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
  end component;
  
 component MainControl is
 Port (Instr1 :in STD_LOGIC_VECTOR(15 downto 13 );
        RegDst :out STD_LOGIC;
         ExtOp :out STD_LOGIC;
         ALUSrc :out STD_LOGIC;
         Branch:out STD_LOGIC;
         Jump :out STD_LOGIC;
         ALUOp :out STD_LOGIC_VECTOR(1 downto 0);
        MemWrite :out STD_LOGIC;
         MemtoReg :out STD_LOGIC;
         RegWrite: out STD_LOGIC;
          BNE :out STD_LOGIC );
end component;
begin
 

    
    --Lab6
    m1 :mpg port map(btn(0),clk,en);
    m2: mpg port map(btn(1),clk,rst);
    inst_IF: IF_instr PORT MAP(en,rst,clk,sw(1),sw(0),x"0000",x"0000",Instr,PCinc);
    inst_ID :ID PORT MAP (RegWrite,Instr,RegDst,clk,en,ExtOp,sum,RD1,RD2,Ext_imm,func,sa);
    inst_MC :MainControl PORT MAP (Instr(15 downto 13),RegDst,ExtOp,ALUSrc,Branch,Jump,ALUOp,MemWrite,MemtoReg,RegWrite,BNE);
    sum<=RD1+RD2;
    Ext_func<="0000000000000"& func;
    Ext_sa<="000000000000000" & sa;
    
    with sw(7 DOWNTO 5) select 
      digits<=Instr when "000",
      PCinc when "001",
      RD1 when "010",
      RD2 when "011",
      sum when "100",
      Ext_imm when "101",
      Ext_func when "110",
      Ext_sa when "111",
      (others=>'X')     WHEN OTHERS;
           
           
           
      display: SSD PORT MAP (clk,digits,an,cat);
      led(10 downto 0)<=AlUOp & RegDst &ExtOp & ALUSrc &Branch &BNE &Jump &MemWrite & MemtoReg & RegWrite;
    
    

end Behavioral;
