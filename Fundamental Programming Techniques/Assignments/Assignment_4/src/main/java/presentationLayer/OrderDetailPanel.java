package presentationLayer;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderDetailPanel extends JPanel implements ActionListener {

    private JLabel lblRightTitle;

    private JScrollPane menuScrollPanel;
    private JButton btnAll;
    private JButton btnBase;
    private JButton btnComposite;
    private JLabel lblLeftInfo;
    private JLabel    lblTitle;
    private JTable menuItems;

    private JButton btnAddItem;


    private JLabel lblTotalSales;
    ;

    private JList orderItemList;

    private JScrollPane orderScrollPanel;
    private int currentOrderID;
    private int orderItemCount;


    private JPanel orderDetailPanel;
    private JPanel menuListPanel;
    private Controller controller;

    public OrderDetailPanel(Controller controller) {
        this.controller = controller;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));


        orderDetailPanel = new JPanel();


        GridBagLayout gbLayout = new GridBagLayout();
        orderDetailPanel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        lblTitle = new JLabel("Order detail");


        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);


        lblLeftInfo = new JLabel("No    Item name                    price");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbLayout.setConstraints(lblLeftInfo, gbc);
        orderDetailPanel.add(lblLeftInfo);

        orderScrollPanel = new JScrollPane();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weighty = 1.0;

        gbLayout.setConstraints(orderScrollPanel, gbc);
        orderDetailPanel.add(orderScrollPanel);

        lblTotalSales = new JLabel("Total : 0");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.gridwidth = 4;

        gbLayout.setConstraints(lblTotalSales, gbc);
        orderDetailPanel.add(lblTotalSales);


        btnAddItem = new JButton("Add");
        btnAddItem.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbLayout.setConstraints(btnAddItem, gbc);
        orderDetailPanel.add(btnAddItem);


        menuListPanel = new JPanel();

        menuListPanel.setLayout(gbLayout);

        menuItems = new JTable(0, 0);

        lblRightTitle = new JLabel("Menu list");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbLayout.setConstraints(lblRightTitle, gbc);
        menuListPanel.add(lblRightTitle);

        menuScrollPanel = new JScrollPane(menuItems);

        menuScrollPanel.setPreferredSize(new Dimension(200, 400));
        gbc.gridy = 1;
        gbc.weighty = 1.0;

        gbLayout.setConstraints(menuScrollPanel, gbc);
        menuListPanel.add(menuScrollPanel);

        btnAll = new JButton("All");
        btnAll.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbLayout.setConstraints(btnAll, gbc);
        menuListPanel.add(btnAll);

        btnBase = new JButton("Base");
        btnBase.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbLayout.setConstraints(btnBase, gbc);
        menuListPanel.add(btnBase);

        btnComposite = new JButton("Composite");
        btnComposite.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbLayout.setConstraints(btnComposite, gbc);
        menuListPanel.add(btnComposite);


        LineBorder border = new LineBorder(Color.BLACK, 1, false);
        menuListPanel.setBorder(border);
        orderDetailPanel.setBorder(border);
        this.add(orderDetailPanel);
        this.add(menuListPanel);


        orderItemList = new JList();
        orderItemList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        orderItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


    }


    private void setTotal(double total) {
        lblTotalSales.setText("Total : " + total);
    }


    public void init() {


        ArrayList<String> list = controller.createOrderItemlList(-1);
        setTotal(0);
        orderItemCount = list.size();
        orderItemList.setListData(list.toArray());
        orderScrollPanel.getViewport().setView(orderItemList);


    }

    private void refleshOrderDetailList(int id) {
        ArrayList<String> list = controller.createOrderItemlList(id);
        setTotal(controller.getRestaurant().computeOrderPrice(id));
        orderItemCount = list.size();
        orderItemList.setListData(list.toArray());

        orderScrollPanel.getViewport().setView(orderItemList);
    }


    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnAddItem) {


            if (btnAddItem.getVerifyInputWhenFocusTarget()) {

                btnAddItem.requestFocusInWindow();
                if (!btnAddItem.hasFocus()) {
                    return;
                }
            }
            if (menuItems.getSelectedRow() == -1) {
                showErrorDialog("error", "select a row");
                return;
            }

            String menuLine = (String) menuItems.getValueAt(menuItems.getSelectedRow(), menuItems.getSelectedColumn());
            if (menuLine == null)
                return;


            controller.getRestaurant().addItemToOrder(controller.getRestaurant().getOrders().size() - 1, menuLine);

            refleshOrderDetailList(controller.getRestaurant().getOrders().size() - 1);


        } else if (ae.getSource() == btnAll) {
            menuItems.setModel(controller.setMenuOrder(0));
        } else if (ae.getSource() == btnBase) {
            menuItems.setModel(controller.setMenuOrder(2));
        } else if (ae.getSource() == btnComposite) {
            menuItems.setModel(controller.setMenuOrder(1));
        }
    }


}