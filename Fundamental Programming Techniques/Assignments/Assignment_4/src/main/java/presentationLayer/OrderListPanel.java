package presentationLayer;

import businessLayer.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class OrderListPanel extends JPanel implements ActionListener {
    private JScrollPane scrollPanel;

    private JButton btnNewOrder;
    private JButton btnGeberateBill;
    private OrderDetailPanel cOrderDetailPanel;
    private JTable menu;
    private Controller controller;
    private JPanel mainPanel;

    public OrderListPanel(Controller controller, JPanel mainPanel, OrderDetailPanel cOrderDetailPanel) {
        this.mainPanel = mainPanel;
        this.controller = controller;
        this.cOrderDetailPanel = cOrderDetailPanel;
        GridBagLayout gbLayout = new GridBagLayout();
        this.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        menu = new JTable(0, 0);
        scrollPanel = new JScrollPane(menu);
        scrollPanel.setPreferredSize(new Dimension(500, 300));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbLayout.setConstraints(scrollPanel, gbc);
        this.add(scrollPanel);


        btnNewOrder = new JButton("New");
        btnNewOrder.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbLayout.setConstraints(btnNewOrder, gbc);
        this.add(btnNewOrder);

        btnGeberateBill = new JButton("Generate Bill");
        btnGeberateBill.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbLayout.setConstraints(btnGeberateBill, gbc);
        this.add(btnGeberateBill);


    }
    public void showOrderList() {
        menu.setModel(controller.setOrder());

    }
    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnNewOrder) {

            controller.getRestaurant().createOrder(new Date());
            for (Order o : controller.getRestaurant().getOrders()) {

                if (o.getOrderID() == (controller.getRestaurant().getOrders().size() - 1)) {
                    controller.getRestaurant().notifyObserver(o);
                }
            }

            changeMainPanel("OrderDetail");


        } else if (ae.getSource() == btnGeberateBill) {
            if (btnGeberateBill.getVerifyInputWhenFocusTarget()) {

                btnGeberateBill.requestFocusInWindow();
                if (!btnGeberateBill.hasFocus()) {

                    return;
                }
            }
            if (menu.getSelectedRow() == -1) {
                showErrorDialog("error", "select a row");
                return;
            }

            String menuLine = (String) menu.getValueAt(menu.getSelectedRow(), 0);


            try {
                controller.getRestaurant().generateBill(Integer.parseInt(menuLine));

            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println(ioException.getMessage());
            }
            showOrderList();
        }
    }

    public void changeMainPanel(String panelName) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, panelName);

    }

}