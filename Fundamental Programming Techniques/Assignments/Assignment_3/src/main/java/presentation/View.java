package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa care implementeaza Presentation  Layer.
 * Aici se face legatura cu celelalte 3 clase de tip GUI.
 */
public class View extends  JFrame{
    JButton b1 = new JButton("Clients");
    JButton b2 = new JButton(" Products");
    JButton b3s = new JButton("Place Orders");
    String spaces = "                          ";
    JLabel j = new JLabel(spaces + "       Choose a tabel         " + spaces);

    private ClientGUI clientGUI;
    private ProductGUI productGUI;
    private OrderGUI orderGUI;

    public View(ClientGUI clientGUI, ProductGUI productGUI, OrderGUI orderGUI) {
        this.clientGUI = clientGUI;
        this.productGUI = productGUI;
        this.orderGUI = orderGUI;


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(380, 150);

        JPanel panel = new JPanel();
        j.setFont(new Font("Chalkboard", Font.PLAIN, 20));
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(153, 124, 150));
        p1.setSize(50, 50);
        panel.add(p1);

        panel.add(j);


        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientGUI.setVisible(true);
            }
        });
        b1.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        panel.add(b1);


        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productGUI.setVisible(true);
            }
        });
        b2.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        panel.add(b2);


        b3s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderGUI.setVisible(true);
            }
        });
        b3s.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        panel.add(b3s);

        panel.setBackground(new Color(153, 124, 150));

        this.add(panel);
    }

}

