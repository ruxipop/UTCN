package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clasa care implementeaza Presentation  Layer pentru tabelul OrderW.
 */
public class OrderGUI  extends JFrame{
    private Controller controller;
    private JTable table;

    JTextField textIdOrder = new JTextField();
    JTextField textIdClient = new JTextField();
    JTextField textIdProduct = new JTextField();
    JTextField textQuantity = new JTextField();

    private DefaultListModel<String> listModel;

    JButton b1 = new JButton("Place Order");
    public OrderGUI(Controller controller) {
        super("Orders");
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
        panel2.setBounds(0, 500, 810, 100);
        JLabel labelIdOr = new JLabel("idOrder:");
        panel2.add(labelIdOr);



        textIdOrder.setColumns(9);
        panel2.add(textIdOrder);

        JLabel labelIdPr = new JLabel("idProduct:");
        panel2.add(labelIdPr);


        textIdProduct.setColumns(9);
        panel2.add(textIdProduct);

        JLabel labelIdClient = new JLabel("idClient:");
        panel2.add(labelIdClient);


        textIdClient.setColumns(10);
        panel2.add(textIdClient);

        JLabel labelQuan = new JLabel("quantity:");
        panel2.add(labelQuan);
        labelIdClient.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelIdOr.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelQuan.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        labelIdPr.setFont(new Font("Chalkboard", Font.PLAIN, 15));
        b1.setFont(new Font("Chalkboard", Font.PLAIN, 15));


        textQuantity.setColumns(11);
        panel2.add(textQuantity);



        panel2.add(b1);









       panel.add(panel2);

        this.add(panel);
    }


    public void addOLinstener(ActionListener avl) {
        b1.addActionListener(avl);

    }
    public void setTableModel(TableModel tableModel) {
        if (tableModel != null){
            table.setModel(tableModel);
        }
        else {
            table.setModel(new DefaultTableModel());
        }
    }

public int getIdClient(){
    if(textIdClient.getText().isEmpty()){
        return -1;

    }
        int id=Integer.parseInt(textIdClient.getText());
        return id;
}
    public int getIdProduct(){
        if(textIdProduct.getText().isEmpty()){
            return -1;

        }
        int id=Integer.parseInt(textIdProduct.getText());
        return id;
    }


    public int getIdOrder(){
        if(textIdOrder.getText().isEmpty()){
            return -1;

        }
        int id=Integer.parseInt(textIdOrder.getText());
        return id;
    }
    public int getQuantity(){
        if(textQuantity.getText().isEmpty()){
            return -1;

        }
        int q=Integer.parseInt(textQuantity.getText());
        return q;
    }

}