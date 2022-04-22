package view;

import model.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.Font;

public class View extends JFrame {
	private Model model;

	private String mesajInstructiuni = "Se selectează tipul operatiei (unare/binare) apoi se scriu polinoamele/polinomul.\n\n" + "Formatul polinomului este  a(n)x^(n)+a(n-1)x^(n-1)+...+a(0)x^0, unde a este coeficientul, și n exponentul.\n\n " +
			"Din partea dreaptă se selectează operația.\n\n" + "Butonul Del va duce la resetarea câmpurilor.\n\n" +
			"Erorile pot apărea în cazul în care un polinom este scris greșit sau în cazul în care ați dorit să efectuați o impărțire la 0";


	//----------------butoane-----------//
	private JButton b1 = new JButton("+");
	private JButton b2 = new JButton("-");
	private JButton b3 = new JButton("*");
	private JButton b4 = new JButton("/");
	private JButton b5 = new JButton("\u03B4");
	private JButton b6 = new JButton("\u0283");
	private JButton b7 = new JButton("Del");
	private JButton b8 = new JButton("Instr");


	//---------------text-field-------//
	private JTextField tffirst = new JTextField(35);
	private JTextField tfsecond = new JTextField(35);
	private JTextField tfresult = new JTextField(50);
	private JTextField tfresult1 = new JTextField(50);


	//---------------label-------//
	private JLabel lname = new JLabel("Polynomial Calculator", JLabel.LEFT);
	private JLabel lresult = new JLabel("Result", JLabel.LEFT);
	private JLabel lrest = new JLabel("Rest", JLabel.LEFT);
	private JLabel lcat = new JLabel("Cât", JLabel.LEFT);
	private JLabel lfirst = new JLabel("First Polynomial", JLabel.LEFT);
	private JLabel lsecond = new JLabel("Second Polynomial", JLabel.LEFT);


	//---------------radio-button-------//
	private JRadioButton binary = new JRadioButton("Binary Operations");
	private JRadioButton unary = new JRadioButton("Unary Operations");
	ButtonGroup grup = new ButtonGroup();


	//---------------panou-------//
	GridBagConstraints c = new GridBagConstraints();//constrangeri pentru aranjare
	JPanel panel = new JPanel(new GridBagLayout());


	public View(Model model) {
		this.model = model;
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setBackground(new Color(120, 80, 90));
		panel.setSize(500, 300);


		//---------------radio-button-------//


		c.gridx = 0;
		c.gridy = 0;
		binary.setFont(new Font("trattatello", Font.CENTER_BASELINE, 30));
		panel.add(binary, c);

		c.gridx++;
		unary.setFont(new Font("trattatello", Font.CENTER_BASELINE, 30));
		panel.add(unary, c);


		grup.add(binary);
		grup.add(unary);


		//----------first-polynomial------------//
		c.gridx = 0;
		c.gridy = 1;

		lfirst.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 20));
		panel.add(lfirst, c);


		c.gridx++;

		tffirst.setOpaque(true);
		tffirst.setBackground(new Color(200, 200, 200));


		panel.add(tffirst, c);


		//----------second-polynomial------------//

		c.gridx = 0;
		c.gridy = 2;

		lsecond.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 20));
		panel.add(lsecond, c);

		c.gridx++;

		tfsecond.setOpaque(true);
		tfsecond.setBackground(new Color(200, 200, 200));

		panel.add(tfsecond, c);


		//----------result------------//

		c.gridx = 0;
		c.gridy = 3;

		lresult.setFont(new Font("Chalkduster", Font.BOLD, 35));
		panel.add(lresult, c);

		lcat.setFont(new Font("Chalkduster", Font.BOLD, 35));
		panel.add(lcat, c);

		c.gridy++;

		lrest.setFont(new Font("Chalkduster", Font.BOLD, 35));
		panel.add(lrest, c);

		c.gridy--;
		c.gridx++;


		c.ipadx = 15;
		c.ipady = 10;
		c.anchor = GridBagConstraints.LAST_LINE_START;

		tfresult.setEditable(false);
		tfresult.setBackground(new Color(204, 190, 190));
		panel.add(tfresult, c);

		c.gridy++;

		tfresult1.setEditable(false);
		tfresult1.setBackground(new Color(204, 190, 190));
		panel.add(tfresult1, c);

		tfresult1.setVisible(false);
		lcat.setVisible(false);
		lrest.setVisible(false);


		//-------butoane-instructiuni--------//

		panel.add(Box.createRigidArea(new Dimension(40, 0)));

		c.gridx = 3;
		c.gridy = 1;
		panel.add(b1, c);

		c.gridx++;
		panel.add(b2, c);

		c.gridx++;
		panel.add(b3, c);

		c.gridx++;
		panel.add(b4, c);

		c.gridy = 2;
		c.gridx = 3;
		panel.add(b5, c);

		c.gridx++;
		panel.add(b6, c);

		c.gridx++;
		panel.add(b7, c);

		c.gridx++;
		panel.add(b8, c);

		//----------setari-principale------//

		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		tfsecond.setEnabled(false);
		tffirst.setEnabled(false);
		tfsecond.setText(null);
		tfresult.setText(null);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);


		b1.setOpaque(true);
		b2.setOpaque(true);
		b3.setOpaque(true);
		b4.setOpaque(true);
		b5.setOpaque(true);
		b6.setOpaque(true);
		b7.setOpaque(true);
		b8.setOpaque(true);
		b8.setBackground(new Color(200, 200, 200));

		b8.setForeground(new Color(120, 80, 90));
		b8.setFont(new Font("Impact", Font.BOLD, 15));


		this.setTitle("Polynomial Calculator");
		this.setContentPane(panel);
		this.pack();


	}

	//----------metode------//


	public void resetPol1(String output) {
		this.tffirst.setText(output);
	}

	public void resetPol2(String output) {
		this.tfsecond.setText(output);
	}


	public String getTextPol1() {
		return tffirst.getText();
	}

	public String getTextPol2() {
		return tfsecond.getText();
	}


	public void setTextOutput(String output) {
		this.tfresult.setText(output);
	}

	public void setTextRest(String output) {
		this.tfresult1.setText(output);
	}


	public void adunareLinstener(ActionListener avl) {   //adunare
		b1.addActionListener(avl);

	}

	public void scadereLinstener(ActionListener avl1) {  //scadere
		b2.addActionListener(avl1);

	}

	public void inmultireLinstener(ActionListener avl2) { //inmultire
		b3.addActionListener(avl2);

	}

	public void impartireLinstener(ActionListener avl3) {  //impartire
		b4.addActionListener(avl3);

	}

	public void derivareLinstener(ActionListener avl4) {  //derivare
		b5.addActionListener(avl4);

	}

	public void integrareLinstener(ActionListener avl5) {  //derivare
		b6.addActionListener(avl5);

	}

	public void deleteLinstener(ActionListener avl6) {   //derivare
		b7.addActionListener(avl6);

	}

	public void instructiuniLinstener(ActionListener avl7) {  //derivare
		b8.addActionListener(avl7);

	}


	public void showMessageDialog() {
		JOptionPane.showMessageDialog(this, mesajInstructiuni);
	}

	public void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}


	public void binarButtonListener(ActionListener e) {
		binary.addActionListener(e);
	}

	public void unarButtonListener(ActionListener e) {
		unary.addActionListener(e);
	}


	public void setIntrariDiv() {
		tfresult1.setVisible(true);
		lcat.setVisible(true);
		lrest.setVisible(true);
		lresult.setVisible(false);


	}

	public void setInvizibile() {
		tfresult1.setVisible(false);
		lcat.setVisible(false);
		lrest.setVisible(false);
		lresult.setVisible(true);


	}


	public void setIntrariUnar() {
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		tffirst.setEnabled(true);
		tfsecond.setEnabled(false);
		tfsecond.setText(null);
		tfresult.setText(null);
		b5.setEnabled(true);
		b5.setBackground(new Color(200, 200, 200));
		b6.setBackground(new Color(200, 200, 200));
		b6.setEnabled(true);
		b7.setBackground(new Color(200, 200, 200));
		b7.setEnabled(true);


	}

	public void setIntratiBinar() {
		b1.setBackground(new Color(200, 200, 200));
		b1.setEnabled(true);
		b2.setBackground(new Color(200, 200, 200));
		b2.setEnabled(true);
		b3.setBackground(new Color(200, 200, 200));
		b3.setEnabled(true);
		b4.setBackground(new Color(200, 200, 200));
		b4.setEnabled(true);
		tffirst.setEnabled(true);
		tfsecond.setEnabled(true);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setBackground(new Color(200, 200, 200));
		b7.setEnabled(true);


	}

}
