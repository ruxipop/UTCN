package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.IDeliveryServiceProcessing;
import businessLayer.Order;
import dataLayer.TextStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class EmployeeGUI extends JFrame implements Observer, ActionListener {
    private JTable cookingTable;

    private JButton finishOrder;
    private JButton btnL;
    private List<String> cooking;
    private JPanel btnPanel;
    private JButton btnLogin;
    private JTextField tfUserID;
    private JPanel loginPanel;
    private JPasswordField pwPassword;
    private String name;

    public EmployeeGUI() {
        cooking = new ArrayList<>();

        cookingTable = new JTable();

        finishOrder = new JButton("Finish Order");

        cookingTable.setFillsViewportHeight(true);
        loginPanel = new JPanel();
        GridBagLayout gbLayout = new GridBagLayout();
        loginPanel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblUserID = new JLabel("UserID:");
        lblUserID.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbLayout.setConstraints(lblUserID, gbc);
        loginPanel.add(lblUserID);

        tfUserID = new JTextField(10);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbLayout.setConstraints(tfUserID, gbc);
        loginPanel.add(tfUserID);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbLayout.setConstraints(lblPassword, gbc);
        loginPanel.add(lblPassword);

        pwPassword = new JPasswordField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbLayout.setConstraints(pwPassword, gbc);
        loginPanel.add(pwPassword);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbLayout.setConstraints(btnLogin, gbc);
        loginPanel.add(btnLogin);


        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        btnPanel.add(finishOrder);


        setLayout(new BorderLayout());
        add(cookingTable, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(loginPanel, BorderLayout.CENTER);
        btnPanel.setVisible(false);
        finishOrder.addActionListener(this);

        this.setMinimumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


    private void setCookingTable(JTable cookingTable) {
        this.cookingTable = cookingTable;
        JScrollPane scrollPane = new JScrollPane(cookingTable);
        add(scrollPane, BorderLayout.CENTER, 0);


    }


    public JTable setOrder(List<String> orders) {

        String[] columns = new String[]{"Order ID"};
        int noRows = orders.size();
        String[][] data = new String[noRows][1];

        int rowIndex = 0;
        if (!orders.isEmpty()) {
            for (String o : orders) {

                data[rowIndex][0] = o;


                rowIndex++;
            }


        } else {
            data = new String[1][1];
            data[0][0] = "No orders ";
        }


        JTable t = new JTable(data, columns);

        return t;
    }

    @Override
    public void update(Observable o, Object arg) {
        Order or = (Order) arg;
        cooking.add(String.valueOf(or.getOrderID()));
        setCookingTable(setOrder(cooking));
        revalidate();
        repaint();
    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showConfirmDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }


    public boolean loginCheck(String inputID, char[] inputPassword) {

        boolean ok = false;
        String a = String.copyValueOf(inputPassword);
        java.util.List<String> employee = TextStream.parseTextEmployee("employee.txt");
        for (int i = 0; i < employee.size(); i++) {
            String manager1 = employee.get(i);
            String[] parts = manager1.split(" ");
            String id = parts[0].trim();
            String password = parts[1].trim();
            System.out.println(password);

            name = parts[2].trim();
            if (id.equals(inputID) && password.equals(a)) {

                ok = true;
            }


        }

        return ok;


    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnLogin) {
            String id = tfUserID.getText();

            if (id.equals("")) {
                showErrorDialog("Error", "Enter ID!");
                return;
            }
            char[] password = pwPassword.getPassword();
            String a = String.copyValueOf(password);

            if (a.equals("")) {
                showErrorDialog("Error", "Enter password!");
                return;
            }

            if (loginCheck(id, password)) {
                showConfirmDialog("Message", "Login success!!");
                tfUserID.setText("");
                pwPassword.setText("");
                loginPanel.setVisible(false);
                btnPanel.setVisible(true);

                this.setTitle("Employee with name " + name);


            }
        } else if (ae.getSource() == finishOrder) {
            int[] selectedRows = cookingTable.getSelectedRows();

            if (selectedRows.length == 0) {
                showErrorDialog("error", "Select items to finish!");
            } else {
                try {
                    for (int row : selectedRows) {

                        cooking.remove(cookingTable.getValueAt(row, 0).toString());

                    }
                } catch (IllegalArgumentException ex) {
                    showErrorDialog("error", ex.getMessage());

                }
            }

            setCookingTable(setOrder(cooking));
            revalidate();
            repaint();
        }


    }

}

