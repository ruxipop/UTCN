-- Pop Ruxandra Maria si Zelenszky Bianca
-- Cutia neagra	

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;
use work.tipuri_package1.all;

-- Entitate
-- Intrari: clk (semnal de tact), reset_g, reset_f, Control (3 biti), Length (3 biti)
-- Iesire: Catod (8 biti)
-- Intrare/Iesire: Anod (4 biti)
entity cutie_neagra_medie is		   
	port(clk, reset_g, reset_f: in std_logic;
	Control: in std_logic_vector(2 downto 0);
	Length : in std_logic_vector(2 downto 0);
	Catod: out std_logic_vector(7 downto 0);
	Anod: inout std_logic_vector(3 downto 0));
end cutie_neagra_medie;

-- Arhitectura 
-- Avem doua componente 
architecture arh_cutie_neagra_medie of cutie_neagra_medie is  
-- Unitatea de comanda
component divizor_anod is
port( led: in std_logic_vector(31 downto 0);
 	clock_anod: in std_logic;  
	catod: out std_logic_vector(7 downto 0);
	anod: out std_logic_vector(3 downto 0));	
end component;	

component divizor_generator is
	port( clk, reset: in std_logic;
	Q: inout std_logic := '0');
end component;

component generator is
	 port( clk, reset: in std_logic;
	 control: in std_logic_vector(2 downto 0);
	 number: out std_logic_vector(7 downto 0));
end component;

component filter is
	port( numar: in std_logic_vector(7 downto 0);
	data_clock: in std_logic;
	length: in std_logic_vector(2 downto 0);
	reset: in std_logic;
	media: out std_logic_vector(7 downto 0);
	numar_out: out std_logic_vector(7 downto 0));
end component;	 

component display is	 
	port( data: in std_logic_vector (3 downto 0);
	catod: out std_logic_vector(7 downto 0));
end component;

-- Unitatea de executie

signal Data_clock: std_logic := '0';    
signal Data_out: std_logic_vector(7 downto 0) := (others=>'0');
signal Data:  std_logic_vector(7 downto 0) := (others=>'0');		
signal Suma: std_logic_vector(7 downto 0) := (others=>'0');	
signal Dis_led: std_logic_vector(31 downto 0);  
signal d_medie: data_1 := (others =>(others=>'0'));
signal d_catod: ies_c := (others =>(others=>'0'));
begin			   

 process(clk) -- se foloseste intrarea clk in proces
 begin
	 Dis_led(7 downto 0) <= d_catod(0);			-- se tin minte in Dis_led numerele memorate in d_catod
	 Dis_led(15 downto 8) <= d_catod(1);
	 Dis_led(23 downto 16) <= d_catod(2);
	 Dis_led(31 downto 24) <= d_catod(3);
	 d_medie(0) <= Data_out(3 downto 0);	-- d_medie(0) retine a doua jumatate din numarul generat 
	 d_medie(1) <= Data_out(7 downto 4);    -- d_medie(1) retine prima jumatate	din numarul generat
	 d_medie(2) <= Suma(3 downto 0);	    -- d_medie(2) retine a doua jumatate din media calculata
	 d_medie(3) <= Suma(7 downto 4);		-- d_medie(3) retine prima jumatate din media calculata
 end process;
	
	C1: divizor_generator port map (clk,reset_g,Data_clock);	   
	C2: generator port map (Data_clock,reset_g,Control,Data);
	C3: filter port map (Data,Data_clock,length,reset_f,Suma,Data_out);	
	C4: divizor_anod port map (Dis_led,clk,Catod,Anod);
	
	-- Se afiseaza pe display numerele generate/media
	C5: display port map(d_medie(0),d_catod(0));	 
	C6: display port map(d_medie(1),d_catod(1));  
	C7: display port map(d_medie(2),d_catod(2));
	C8: display port map(d_medie(3),d_catod(3));	 
	
end architecture;
	
	
