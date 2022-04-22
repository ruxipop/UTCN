library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity test_env is
    PORT( clk: in STD_LOGIC;
          btn: in STD_LOGIC_VECTOR(4 downto 0);
          sw: in STD_LOGIC_VECTOR (15 downto 0);
          led: out STD_LOGIC_VECTOR(15 downto 0);
          an: out STD_LOGIC_VECTOR(3 downto 0);
          cat: out STD_LOGIC_VECTOR(6 downto 0));
end test_env;

architecture Behavioral of test_env is

    ---mpg--
    
    signal en,rst:STD_LOGIC;
    
   ---IF---
   signal Instr,JumpAddress,BranchAddress,PCinc :STD_LOGIC_VECTOR(15 downto 0);
   signal Jump,PCSrc :STD_LOGIC;
   
   --ID--
   signal  RegWrite,ExtOp,RegDst,sa:STD_LOGIC;
   signal WriteData,RD1,RD2,Ext_imm:STD_LOGIC_VECTOR(15 downto 0);
   signal func:STD_LOGIC_VECTOR(2 downto 0);
   
   
   --MainControl--
    signal ALUSrc,branch,MemWrite,MemtoReg,br_ne,br_gez:STD_LOGIC;
    signal ALUOp:STD_LOGIC_VECTOR(1 downto 0);
    
    --EX--
    signal Zero:STD_LOGIC;
    signal gez:STD_LOGIC;
    
    --MEM-
       signal ALURes,MemData,ALUResOut: STD_LOGIC_VECTOR(15 downto 0);
         signal digits:STD_LOGIC_VECTOR(15 downto 0);
           
         
            
              
             
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
          BNE :out STD_LOGIC;
          BGEZ :out STD_LOGIC );
end component;
component MEM is
 Port (MemWrite:in STD_LOGIC;
       ALUResIn:in std_logic_vector(15 DOWNTO 0);
       RD2:in std_logic_vector(15 DOWNTO 0);
       CLK:in STD_LOGIC;
       EN:in STD_LOGIC;
       MemData:out std_logic_vector(15 DOWNTO 0);
         ALUResOut:out std_logic_vector(15 DOWNTO 0));
end component;
component EX is
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
end component;

begin
 

    
    --Lab6
    m1 :mpg port map(btn(0),clk,en);
    m2: mpg port map(btn(1),clk,rst);
    inst_IF: IF_instr PORT MAP(en,rst,clk,PCSrc,Jump,JumpAddress,BranchAddress,Instr,PCinc);
    inst_ID :ID PORT MAP (RegWrite,Instr,RegDst,clk,en,ExtOp,WriteData,RD1,RD2,Ext_imm,func,sa);
    inst_MC :MainControl PORT MAP (Instr(15 downto 13),RegDst,ExtOp,ALUSrc,Branch,Jump,ALUOp,MemWrite,MemtoReg,RegWrite,br_ne,br_gez);
    instr_EX:EX PORT MAP (RD1,RD2,ALUSrc,Ext_imm,sa,func,ALUOp,PCinc,gez,Zero,ALURes,BranchAddress);
    instr_MEM :MEM PORT MAP (MemWrite,ALURes,RD2,clk,en,MemData,ALUResOut);
    
    JumpAddress<=Instr(12 downto 0) &PCinc(15 downto 13);
   
   PCSrc<=(Branch and Zero) or (br_ne and not(Zero)) or (gez and br_gez);
    
    WriteData<=ALUResOut when MemtoReg='0' else MemData;

   
    with sw(7 DOWNTO 5) select 
      digits<=Instr when "000",
      PCinc when "001",
      RD1 when "010",
      RD2 when "011",
      Ext_imm when "100",
      AluRes when "101",
      MemData when "110",
      WriteData when "111",
      (others=>'X')     WHEN OTHERS;
           
           
           
      display: SSD PORT MAP (clk,digits,an,cat);
      led(11 downto 0)<=RegDst &ExtOp&ALUSrc&Branch&br_ne &br_gez &Jump&MemWrite &MemtoReg & RegWrite &ALUOp;
    
    
    

end Behavioral;
