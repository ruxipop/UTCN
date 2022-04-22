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
   signal Instr,JumpAddress,BranchAddress,PCinc :STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
   signal Jump,PCSrc :STD_LOGIC:='0';
   
   --ID--
   signal  RegWrite,ExtOp,RegDst,sa:STD_LOGIC:='0';
   signal WriteData,RD1,RD2,Ext_imm:STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
   signal func:STD_LOGIC_VECTOR(2 downto 0):=(others=>'0');
    signal rt,rd:STD_LOGIC_VECTOR(2 downto 0):=(others=>'0');
   
   --MainControl--
    signal ALUSrc,branch,MemWrite,MemtoReg,br_ne,br_gez:STD_LOGIC:='0';
    signal ALUOp:STD_LOGIC_VECTOR(1 downto 0):=(others=>'0');
    
    --EX--
    signal Zero:STD_LOGIC:='0';
    signal gez:STD_LOGIC:='0';
    
    --MEM-
       signal ALURes,MemData,ALUResOut: STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
         signal digits:STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
           
              signal Ext_func,Ext_sa:STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
            signal rWA: STD_LOGIC_VECTOR(2 downto 0):=(others=>'0');
            signal REG_IF_ID :STD_LOGIC_VECTOR(31 downto 0):=(others=>'0');
             signal REG_ID_EX :STD_LOGIC_VECTOR(83 downto 0):=(others=>'0');
                 signal REG_EX_MEM :STD_LOGIC_VECTOR(58 downto 0):=(others=>'0');
                signal REG_MEM_WB :STD_LOGIC_VECTOR(36 downto 0):=(others=>'0');
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
  WA :in STD_LOGIC_VECTOR(2 downto 0);
   CLK: in STD_LOGIC;
   EN :in STD_LOGIC;
   ExtOp: in STD_LOGIC;
   WD: in STD_LOGIC_VECTOR(15 downto 0);
   RD1: out STD_LOGIC_VECTOR(15 downto 0);
    RD2: out STD_LOGIC_VECTOR(15 downto 0);
    Ext_imm : out STD_LOGIC_VECTOR (15 downto 0);
     rt :out STD_LOGIC_VECTOR(2 downto 0);
     rd :out STD_LOGIC_VECTOR(2 downto 0);
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
        BranchAddress: out std_logic_vector(15 downto 0);
        rt: in std_logic_vector(2 downto 0);
           rd: in std_logic_vector(2 downto 0);
            RegDst :in STD_LOGIC;
              rWA: out std_logic_vector(2 downto 0)
         );
end component;

begin
 --------IF/ID-----------
 process(clk)
 begin 
 if rising_edge(clk) then
   if en='1' then
   REG_IF_ID(31 downto 16)<=Instr;
   REG_IF_ID(15 DOWNTO 0)<=PCinc;
   end if;
   end if;
   end  process;
-----------ID/EX------------
   process(clk)
   begin
   if rising_edge(clk) then
   if en='1' then
   REG_ID_EX(83)<=RegDst;
   REG_ID_EX(82)<=ALUSrc;
   REG_ID_EX(81)<=Branch;
   REG_ID_EX(80)<=br_gez;
      REG_ID_EX(79)<=br_ne;
   REG_ID_EX(78 downto 77)<=ALUOp;
   REG_ID_EX(76)<=MemWrite;
   REG_ID_EX(75)<=MemtoReg;
   REG_ID_EX(74)<=RegWrite;
   REG_ID_EX(73 downto 58)<=RD1;
      REG_ID_EX(57 downto 42)<=RD2;
         REG_ID_EX(41 downto 26)<=Ext_imm;
      REG_ID_EX(25 downto 23)<=func;
         REG_ID_EX(22)<=sa;
      REG_ID_EX(21 downto 19)<=rd;
       REG_ID_EX(18 downto 16)<=rt;
          REG_ID_EX(15 downto 0)<=REG_IF_ID(15 DOWNTO 0);
          end if;
          end if;
          end process;
          ------------------EX/MEM-----------------
          process(clk)
          begin
          if rising_edge(clk) then
          if en='1' then
          REG_EX_MEM(58)<=REG_ID_EX(79);
          REG_EX_MEM(57)<=REG_ID_EX(80);
          REG_EX_MEM(56)<= REG_ID_EX(81);
                REG_EX_MEM(55)<=REG_ID_EX(76);
                 REG_EX_MEM(54)<=REG_ID_EX(75);
                 REG_EX_MEM(53)<=  REG_ID_EX(74);
               
                
                     REG_EX_MEM(52)<=Zero;
                         REG_EX_MEM(51)<=gez;
                             REG_EX_MEM(50 DOWNTO 35)<=BranchAddress;
                                 REG_EX_MEM(34 DOWNTO 19)<=ALURes;
                                 REG_EX_MEM(18 DOWNTO 16)<=rWA;
                                 REG_EX_MEM(15 DOWNTO 0)<=REG_ID_EX(57 downto 42);
                                 END IF;
                                 END IF;
                                 END PROCESS;
      ------------------------------MEM/WB-------------
      PROCESS(clk)
      begin
      if rising_edge(clk) then
      if en='1' then
      REG_MEM_WB (36)<=    REG_EX_MEM(53);
      REG_MEM_WB(35)<=REG_EX_MEM(54);
      REG_MEM_WB(34 downto 19)<=ALUResOut;
      REG_MEM_WB(18 downto 3)<=MemData;
      REG_MEM_WB(2 downto 0)<= REG_EX_MEM(18 DOWNTO 16);
      end if;
      end if;
      end process;
    --Lab6
    m1 :mpg port map(btn(0),clk,en);
    m2: mpg port map(btn(1),clk,rst);
    inst_IF: IF_instr PORT MAP(en,rst,clk,PCSrc,Jump,JumpAddress, REG_EX_MEM(50 DOWNTO 35),Instr,PCinc);
    inst_ID :ID PORT MAP ( REG_MEM_WB(36),REG_IF_ID(31 downto 16),  REG_MEM_WB(2 downto 0),clk,en,ExtOp,WriteData,RD1,RD2,Ext_imm,rt,rd,func,sa);
    inst_MC :MainControl PORT MAP (REG_IF_ID(31 downto 29),RegDst,ExtOp,ALUSrc,Branch,Jump,ALUOp,MemWrite,MemtoReg,RegWrite,br_ne,br_gez);
    instr_EX:EX PORT MAP (REG_ID_EX(73 downto 58),  REG_ID_EX(57 downto 42), REG_ID_EX(82), REG_ID_EX(41 downto 26),REG_ID_EX(22), REG_ID_EX(25 downto 23), REG_ID_EX(78 downto 77),REG_ID_EX(15 downto 0),gez,Zero,ALURes,BranchAddress,REG_ID_EX(18 DOWNTO 16),REG_ID_EX(21 downto 19),REG_ID_EX(83),rWA);
    instr_MEM :MEM PORT MAP (REG_EX_MEM(55),REG_EX_MEM(34 DOWNTO 19), REG_EX_MEM(15 DOWNTO 0),clk,en,MemData,ALUResOut);
    
    JumpAddress<=REG_IF_ID(15 DOWNTO 13) & REG_IF_ID(28 downto 16) ;
   
   PCSrc<=(REG_EX_MEM(56) and REG_EX_MEM(52)) or ( REG_EX_MEM(58) and not(REG_EX_MEM(52))) or (REG_EX_MEM(51) and    REG_EX_MEM(57));
    
    WriteData<=REG_MEM_WB(34 downto 19) when REG_MEM_WB(35)='0' else REG_MEM_WB(18 downto 3);
 
   
    with sw(7 DOWNTO 5) select 
      digits<=Instr when "000", 
      PCinc when "001",
      REG_ID_EX(73 DOWNTO 58) when "010",  
      REG_ID_EX(57 DOWNTO 42) when "011",
     REG_ID_EX(41 DOWNTO 26) when "100", 
      AluRes when "101", 
      MemData when "110", 
      WriteData when "111",
    
      (others=>'0')     WHEN OTHERS;
           
           
           
      display: SSD PORT MAP (clk,digits,an,cat);
      led(11 downto 0)<=RegDst &ExtOp&ALUSrc&Branch&br_ne &br_gez &Jump&MemWrite &MemtoReg & RegWrite &ALUOp;
    
    
    

end Behavioral;