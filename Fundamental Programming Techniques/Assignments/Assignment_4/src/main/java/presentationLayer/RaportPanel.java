package presentationLayer;

import businessLayer.MenuItem;
import businessLayer.Order;
import dataLayer.TextFileWriter;
import dataLayer.TextStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RaportPanel extends JPanel implements ActionListener {
    private Controller controller;
    private JLabel nrProduct;
    private JTextField tnrProducts;
    private JPanel raport2;
    private JButton btnRaport2;
    private JLabel startHour;
    private JLabel endHour;
    private JTextField tStartHour;
    private JTextField tEndHour;

    private JPanel raport1;
    private JButton btnRaport1;

    private JPanel raport3;
    private JButton btnRaport3;
    private JLabel nrTimes;
    private JTextField tNrTimes;
    private JLabel value;
    private JTextField tValue;

    private JPanel raport4;
    private JButton btnRaport4;
    private JLabel date;
    private JTextField tDate;


    public RaportPanel(Controller controller) {
        this.controller = controller;
        raport2 = new JPanel(new BorderLayout());
        raport1 = new JPanel(new BorderLayout());
        raport3 = new JPanel(new BorderLayout());
        raport4 = new JPanel(new BorderLayout());
        GridBagLayout gbLayout = new GridBagLayout();
        raport1.setLayout(gbLayout);
        raport2.setLayout(gbLayout);
        raport3.setLayout(gbLayout);
        raport4.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        startHour = new JLabel("Start hour");
        raport1.add(startHour, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        tStartHour = new JTextField(10);
        raport1.add(tStartHour, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        endHour = new JLabel("End hour");
        raport1.add(endHour, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        tEndHour = new JTextField(10);
        raport1.add(tEndHour, gbc);
        btnRaport1 = new JButton("Generate R1");
        gbc.gridx = 1;
        gbc.gridy = 2;
        raport1.add(btnRaport1, gbc);
        btnRaport1.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;

        nrProduct = new JLabel("No. products");
        raport2.add(nrProduct, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        tnrProducts = new JTextField(10);
        raport2.add(tnrProducts, gbc);
        btnRaport2 = new JButton("Generate R2");
        gbc.gridx = 1;
        gbc.gridy = 2;
        raport2.add(btnRaport2, gbc);
        btnRaport2.addActionListener(this);


        gbc.gridx = 0;
        gbc.gridy = 0;
        nrTimes = new JLabel("No.");
        raport3.add(nrTimes, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        tNrTimes = new JTextField(10);
        raport3.add(tNrTimes, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        value = new JLabel("Value");
        raport3.add(value, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        tValue = new JTextField(10);
        raport3.add(tValue, gbc);
        btnRaport3 = new JButton("Generate R3");
        gbc.gridx = 1;
        gbc.gridy = 2;
        raport3.add(btnRaport3, gbc);
        btnRaport3.addActionListener(this);


        gbc.gridx = 0;
        gbc.gridy = 0;
        date = new JLabel("Enter date");
        raport4.add(date, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        tDate = new JTextField(10);
        raport4.add(tDate, gbc);

        btnRaport4 = new JButton("Generate R4");
        gbc.gridx = 1;
        gbc.gridy = 1;
        raport4.add(btnRaport4, gbc);
        btnRaport4.addActionListener(this);


        this.add(raport1);
        this.add(raport2);
        this.add(raport3);
        this.add(raport4);
    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }


    public java.util.List<Order> findOrderByDate(final List<Order> list, final int end, final int start) {

        return list.stream().filter(p -> p.getDate().getHours() <= end && p.getDate().getHours() >= start).collect(Collectors.toList());

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnRaport1) {
            int endH = -1, startH = -1;
            if (tStartHour.getText().isEmpty()) {
                showErrorDialog("Error", "Enter start hour");
            } else {
                startH = Integer.parseInt(tStartHour.getText());
            }
            if (tEndHour.getText().isEmpty()) {
                showErrorDialog("Error", "Enter end hour");
            } else {
                endH = Integer.parseInt(tEndHour.getText());
            }
            if (endH != -1 && startH != -1) {
                List<Order> list = new ArrayList<Order>(controller.getRestaurant().getOrders());
                List<Order> list2 = new ArrayList<>();

                list2 = findOrderByDate(list, endH, startH);
                if (!list2.isEmpty()) {
                    for (Order o : list2) {
                        try {
                            TextFileWriter.generateRaport1(list2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    showErrorDialog("Error", "No orders between this hours");
                }
            }
            tStartHour.setText("");
            tEndHour.setText("");

        } else if (ae.getSource() == btnRaport2) {

            int nr = 0;


            if (tnrProducts.getText().isEmpty()) {
                showErrorDialog("Error", "Enter no");
            } else {
                nr = Integer.parseInt(tnrProducts.getText());
            }
            List<MenuItem> itemForOrder = new ArrayList<MenuItem>();
            for (Order o : controller.getRestaurant().getOrders()) {
                for (MenuItem item : controller.getRestaurant().getOrderList().get(o))
                    itemForOrder.add(item);
            }

            if (!tnrProducts.getText().isEmpty()) {
                List<String> m = new ArrayList<>();

                Map<String, Long> lista = controller.findRaport2();
                List<String> products = new ArrayList<>(lista.keySet());
                List<Long> no = new ArrayList<>(lista.values());

                for (int i = 0; i < lista.size(); i++) {
                    if (no.get(i) > nr) {
                        m.add(products.get(i));
                    }
                }
                if (m.isEmpty()) {
                    showErrorDialog("error", "not exist");
                } else {
                    try {
                        TextFileWriter.generateRaport2(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            tnrProducts.setText("");
        } else if (ae.getSource() == btnRaport3) {
            int nrTime = 0;
            int value = 0;
            if (tNrTimes.getText().isEmpty()) {

                showErrorDialog("Error", "Enter no time");
            } else {
                nrTime = Integer.parseInt(tNrTimes.getText());
            }
            if (tValue.getText().isEmpty()) {

                showErrorDialog("Error", "Enter value");
            } else {
                value = Integer.parseInt(tValue.getText());
            }

            if (!tValue.getText().isEmpty() && !tNrTimes.getText().isEmpty()) {

                List<Order> order = controller.getRestaurant().getOrders();
                List<Integer> id = new ArrayList<>();
                Map<Integer, Long> item = controller.findRaport3(order, value);
                List<Integer> client = new ArrayList<>(item.keySet());
                List<Long> number = new ArrayList<>(item.values());

                for (int i = 0; i < item.size(); i++) {
                    if (number.get(i) > nrTime) {
                        id.add(client.get(i));
                    }
                }
                if (id.isEmpty()) {
                    showErrorDialog("error", "not exist");
                } else {
                    try {
                        TextFileWriter.generateRaport3(id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            tValue.setText("");
            tNrTimes.setText("");

        } else if (ae.getSource() == btnRaport4) {

            String date = "";

            if (tDate.getText().isEmpty()) {
                showErrorDialog("Error", "Enter date ");
            } else {
                date = tDate.getText();
            }

            if (!TextStream.reprezentareDate(date)) {
                showErrorDialog("Error", "Format date is not correct");


            } else {

                LocalDate l = LocalDate.parse(date);

                Map<String, Long> product = controller.findRaport4(l);
                if (product.isEmpty()) {

                    showErrorDialog("Error", "Not exist");
                } else {
                    try {
                        TextFileWriter.generateRaport4(product);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            tDate.setText("");
        }
    }
}






