package presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clasa care implementeaza Presentation  Layer pentru tabelul Product.
 */
public class ProductGUI  extends  JFrame{

    private Controller controller;
    private JTable table;
    JTextField textId = new JTextField();
    JTextField textName = new JTextField();
    JTextField textQuantity = new JTextField();
    JTextField textprice = new JTextField();
    JButton b1 = new JButton("Add");
    JButton b2 = new JButton("Remove");
    JButton b3 = new JButton("Edit");

    public ProductGUI(Controller controller) {
        super("Products");
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        table = new JTable();
        table.setBackground(new Color(197, 194, 220));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 910, 500);
        panel.add(scrollPane);

        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 500, 890, 100);

        JLabel labelId = new JLabel("idProduct:");
        panel2.add(labelId);


        textId.setColumns(5);
        panel2.add(textId);

        JLabel labelName = new JLabel("nameProduct:");
        panel2.add(labelName);


        textName.setColumns(15);
        panel2.add(textName);

        JLabel labelAddress = new JLabel("quantity:");
        panel2.add(labelAddress);


        textQuantity.setColumns(10);
        panel2.add(textQuantity);

        JLabel labelAge = new JLabel("price:");
        panel2.add(labelAge);


        textprice.setColumns(13);
        panel2.add(textprice);

        labelAddress.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelAge.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelName.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelId.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelAddress.setFont(new Font("Chalkboard", Font.PLAIN, 15));
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

    public void removePLinstener(ActionListener avl) {
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

    public int getQuantity() {
        if (textQuantity.getText().isEmpty()) {
            return 0;
        }

        int q = Integer.parseInt(textQuantity.getText());

        return q;
    }

    public double getPrice() {
        if (textprice.getText().isEmpty()) {
            return -1;
        }
        double p = Double.parseDouble(textprice.getText());
        return p;
    }
}
