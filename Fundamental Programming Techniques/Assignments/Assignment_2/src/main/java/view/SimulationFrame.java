package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {


    private JButton b1 = new JButton("Run simulation");
    private JButton b2 = new JButton("Stop");
    private JTextField tfnbc = new JTextField(5);
    private JTextField tfnbq = new JTextField(5);
    private JTextField tfsr = new JTextField(5);
    private JTextField tfartmin = new JTextField(5);
    private JTextField tfartmax = new JTextField(5);
    private JTextField tfsermax = new JTextField(5);
    private JTextField tfsermin = new JTextField(5);



    private JLabel lname = new JLabel("Queue Simulator", JLabel.LEFT);
    private JLabel lnbc = new JLabel("Number of clients", JLabel.LEFT);
    private JLabel lnbq = new JLabel("Number of queues", JLabel.LEFT);
    private JLabel lsr = new JLabel("Simulation duration", JLabel.LEFT);
    private JLabel lartmin = new JLabel("Minimum arrival time", JLabel.LEFT);
    private JLabel lartmax = new JLabel("Maximum arrival time", JLabel.LEFT);
    private JLabel lservmin = new JLabel("Minimum service time", JLabel.LEFT);
    private JLabel lservmax = new JLabel("Maximum service time", JLabel.LEFT);
    private JTextArea ltime = new JTextArea();

    GridBagConstraints c = new GridBagConstraints();//constrangeri pentru aranjare
    private JPanel panel = new JPanel(new GridBagLayout());

    public SimulationFrame() {


        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 10, 10, 10);


        c.gridx = 0;
        c.gridy = 0;
        lnbc.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lnbc, c);

        c.gridx = 1;
        panel.add(tfnbc, c);

        c.gridx = 0;
        c.gridy = 1;
        lnbq.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lnbq, c);

        c.gridx = 1;
        panel.add(tfnbq, c);

        c.gridx = 0;
        c.gridy = 2;
        lsr.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lsr, c);

        c.gridx = 1;
        panel.add(tfsr, c);

        c.gridx = 0;
        c.gridy = 3;
        lartmin.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lartmin, c);

        c.gridx = 1;

        panel.add(tfartmin, c);


        c.gridx = 0;
        c.gridy = 4;
        lartmax.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lartmax, c);

        c.gridx = 1;
        panel.add(tfartmax, c);
        c.gridx = 0;
        c.gridy = 5;
        lservmin.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lservmin, c);

        c.gridx = 1;
        panel.add(tfsermin, c);
        c.gridx = 0;
        c.gridy = 6;
        lservmax.setFont(new Font("Papyrus", Font.CENTER_BASELINE, 15));
        panel.add(lservmax, c);

        c.gridx = 1;
        panel.add(tfsermax, c);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 7;
        c.anchor = GridBagConstraints.CENTER;
        b1.setForeground(new Color(149, 124, 130));
        b1.setFont(new Font("Impact", Font.BOLD, 20));
        panel.add(b1, c);
        panel.add(b2, c);
        b2.setVisible(false);
        b2.setForeground(new Color(149, 124, 130));


        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Queue Simulation"));


        add(panel);

        pack();
        setLocationRelativeTo(null);


        this.setTitle("Queue Simulator");
        this.setContentPane(panel);
        this.pack();

    }


    public JTextArea getLtime() {
        return ltime;
    }

    public void setLabel(String s) {
        ltime.setText(ltime.getText() + s);
    }

    public void resetLabel() {
        ltime.setText("");
    }


    public String getNbClients() {
        return this.tfnbc.getText();
    }

    public String getNbQueues() {
        return this.tfnbq.getText();
    }

    public String getSimTime() {
        return this.tfsr.getText();
    }

    public String getMinArv() {
        return this.tfartmin.getText();
    }

    public String getMaxArv() {
        return this.tfartmax.getText();
    }

    public String getMinServ() {
        return this.tfsermin.getText();
    }

    public String getMaxServ() {
        return this.tfsermax.getText();
    }

    public void setInvisible() {
        b1.setVisible(false);
        tfnbc.setVisible(false);
        tfnbq.setVisible(false);
        tfsr.setVisible(false);
        tfartmin.setVisible(false);
        tfartmax.setVisible(false);
        tfsermax.setVisible(false);
        tfsermin.setVisible(false);
        lname.setVisible(false);
        ;
        lnbc.setVisible(false);
        lnbq.setVisible(false);
        lsr.setVisible(false);
        lartmin.setVisible(false);
        lartmax.setVisible(false);
        lservmin.setVisible(false);
        lservmax.setVisible(false);

        b2.setVisible(true);

        panel.add(b2);

    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    public void simulateLinstener(ActionListener avl) {
        b1.addActionListener(avl);

    }
    public void stopLinstener(ActionListener avl) {
        b2.addActionListener(avl);

    }
}
