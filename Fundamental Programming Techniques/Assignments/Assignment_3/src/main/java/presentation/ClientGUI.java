package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clasa care implementeaza Presentation  Layer pentru tabelul Client.
 */
public class ClientGUI extends JFrame {
    JTextField textId = new JTextField();
    JTextField textName = new JTextField();
    JTextField textAddress = new JTextField();
    JTextField textAge = new JTextField();
    JButton b1 = new JButton("Add");
    JButton b2 = new JButton("Remove");
    JButton b3 = new JButton("Edit");
    private Controller controller;
    private JTable table;

    public ClientGUI(Controller controller) {
        super("Clients");
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        table = new JTable();
        table.setBackground(new Color(197, 194, 220));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 900, 500);
        panel.add(scrollPane);

        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 500, 890, 100);

        JLabel labelId = new JLabel("idClient:");
        panel2.add(labelId);


        textId.setColumns(5);
        panel2.add(textId);

        JLabel labelName = new JLabel("name:");
        panel2.add(labelName);


        textName.setColumns(15);
        panel2.add(textName);

        JLabel labelAddress = new JLabel("address:");
        panel2.add(labelAddress);


        textAddress.setColumns(15);
        panel2.add(textAddress);

        JLabel labelAge = new JLabel("age:");
        panel2.add(labelAge);


        textAge.setColumns(15);
        panel2.add(textAge);

        labelAddress.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelAge.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelName.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelId.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        b1.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        b2.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        b3.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        panel2.add(b1);
        panel2.add(b3);
        panel2.add(b2);


        panel.add(panel2);

        this.add(panel);
    }


    public void setTableModel(TableModel tableModel) {
        if (tableModel != null) {
            table.setModel(tableModel);
        } else {
            table.setModel(new DefaultTableModel());
        }
    }


    public void addLinstener(ActionListener avl) {
        b1.addActionListener(avl);

    }

    public void removeLinstener(ActionListener avl) {
        b2.addActionListener(avl);

    }

    public void editLinstener(ActionListener avl) {
        b3.addActionListener(avl);

    }

    public int getID() {
        if (textId.getText().isEmpty()) {
            return -1;

        }
        int id = Integer.parseInt(textId.getText());
        return id;
    }

    public String getName() {
        if (textName.getText().isEmpty()) {
            return null;

        }
        return textName.getText();
    }

    public String getAddress() {
        if (textAddress.getText().isEmpty()) {
            return null;

        }
        return textAddress.getText();
    }

    public int getAge() {
        if (textAge.getText().isEmpty()) {
            return -1;

        }
        int age = Integer.parseInt(textAge.getText());
        return age;
    }
}
