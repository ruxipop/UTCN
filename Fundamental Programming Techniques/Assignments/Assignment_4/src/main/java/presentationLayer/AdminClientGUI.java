package presentationLayer;

import businessLayer.IDeliveryServiceProcessing;
import dataLayer.DeliverySerializer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AdminClientGUI extends JFrame implements ActionListener {
    private Container con;
    private IDeliveryServiceProcessing restaurant;

    private Controller controller;
    private JMenuBar menuBar;
    private JPanel headPanel;
    private JButton headBtnLogout;
    private static JPanel mainPanel;
    private OrderDetailPanel cOrderDetailPanel;
    private SearchMenu cSearchMenu;
    private JPanel mainBtnsPanel;
    private JButton mainBtnShowMenu;
    private JButton headSaveR;
    private JButton mainBtnManageOrder;
    private JButton mainBtnSearchMenu;
    private JButton headImport;
    private JButton mainBtnManageMenuItem;
    private JButton mainBtnShowRaport;
    private JButton headBtnLogin;
    private LoginPanel cLoginPanel;
    private OrderListPanel cOrderListPanel;
    private MenuManagementPanel cMenuManagementPanel;
    private RaportPanel cRaportPanel;
    private MenuListPanel cMenuListPanel;

    private final static int WINDOW_X = 100;
    private final static int WINDOW_Y = 100;
    private final static int WINDOW_WIDTH = 900;
    private final static int WINDOW_HEIGHT = 600;

    public AdminClientGUI(IDeliveryServiceProcessing restaurant, Controller controller) {
        this.controller = controller;
        this.restaurant = restaurant;
        this.con = getContentPane();
        setBounds(WINDOW_X, WINDOW_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMasterPanel();
        cLoginPanel = new LoginPanel();
        mainPanel.add("Login", cLoginPanel);
        cMenuListPanel = new MenuListPanel(controller);
        mainPanel.add("MenuList", cMenuListPanel);
        cSearchMenu = new SearchMenu(controller);
        mainPanel.add("MenuSearch", cSearchMenu);
        cMenuManagementPanel = new MenuManagementPanel(controller);
        cOrderListPanel = new OrderListPanel(controller, mainPanel, cOrderDetailPanel);
        mainPanel.add("OrderList", cOrderListPanel);
        cOrderDetailPanel = new OrderDetailPanel(controller);
        mainPanel.add("OrderDetail", cOrderDetailPanel);
        mainPanel.add("MenuManagement", cMenuManagementPanel);
        cLoginPanel = new LoginPanel();
        cRaportPanel = new RaportPanel(controller);
        mainPanel.add("RaportPanel", cRaportPanel);
        mainPanel.add("Login", cLoginPanel);
        changeMode(MODE_ANONIM);
    }
    private void createMasterPanel() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        con.setLayout(new BorderLayout());
        headPanel = new JPanel();
        headPanel.setBackground(Color.GRAY);
        headBtnLogin = new JButton("Login");
        headBtnLogin.addActionListener(this);
        headBtnLogout = new JButton("Logout");
        headBtnLogout.addActionListener(this);
        headSaveR = new JButton("Save Data");
        headImport = new JButton("Import products");
        headSaveR.addActionListener(this);
        headBtnLogout.setEnabled(false);
        headBtnLogin.setEnabled(false);
        headImport.setVisible(false);
        headPanel.add(headBtnLogin, BorderLayout.EAST);
        headPanel.add(headBtnLogout, BorderLayout.EAST);
        headPanel.add(headSaveR, BorderLayout.EAST);
        headPanel.add(headImport, BorderLayout.EAST);
        con.add(headPanel, BorderLayout.SOUTH);
        mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new CardLayout());
        con.add(mainPanel, BorderLayout.CENTER);
        mainBtnsPanel = new JPanel();
        mainBtnsPanel.setLayout(new GridLayout(0, 1));
        mainBtnShowMenu = new JButton("Show menu");
        mainBtnShowMenu.addActionListener(this);
        mainBtnsPanel.add(mainBtnShowMenu);
        mainBtnSearchMenu = new JButton("Search Menu");
        mainBtnSearchMenu.addActionListener(this);
        mainBtnsPanel.add(mainBtnSearchMenu);
        mainBtnManageOrder = new JButton("Order management");
        mainBtnManageOrder.addActionListener(this);
        mainBtnsPanel.add(mainBtnManageOrder);
        mainBtnManageMenuItem = new JButton("Manage  items");
        mainBtnManageMenuItem.addActionListener(this);
        mainBtnsPanel.add(mainBtnManageMenuItem);
        mainBtnShowRaport = new JButton("Generate reports");
        mainBtnShowRaport.addActionListener(this);
        mainBtnsPanel.add(mainBtnShowRaport);
        con.add(mainBtnsPanel, BorderLayout.WEST);
    }
    public final static byte MODE_ANONIM = 0;
    public final static byte MODE_CLIENT = 1;
    public final static byte MODE_ADMIN = 2;

    public void changeMode(byte state) {
        switch (state) {
            case MODE_ANONIM:
                cLoginPanel.setVisible(false);
                mainBtnShowMenu.setEnabled(false);
                mainBtnManageOrder.setEnabled(false);
                mainBtnSearchMenu.setEnabled(false);
                mainBtnManageMenuItem.setEnabled(false);

                mainBtnShowRaport.setEnabled(false);
                break;
            case MODE_CLIENT:
                cMenuListPanel.setButtonI();
                mainBtnShowMenu.setEnabled(true);
                mainBtnManageOrder.setEnabled(true);

                mainBtnManageMenuItem.setEnabled(false);
                mainBtnSearchMenu.setEnabled(true);
                mainBtnShowRaport.setEnabled(false);
                break;
            case MODE_ADMIN:
                cMenuListPanel.setButtonV();

                mainBtnShowMenu.setEnabled(true);
                mainBtnManageOrder.setEnabled(false);

                mainBtnManageMenuItem.setEnabled(true);

                mainBtnShowRaport.setEnabled(true);
                break;
        }
    }

    final static int DIALOG_YES = JOptionPane.YES_OPTION;


    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showConfirmDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public int showYesNoDialog(String title, String message) {
        int option = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return option;
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == mainBtnShowMenu) {
            changeMainPanel("MenuList");


        } else if (ae.getSource() == mainBtnSearchMenu) {

            changeMainPanel("MenuSearch");
        } else if (ae.getSource() == mainBtnManageOrder) {

            cOrderListPanel.showOrderList();

            changeMainPanel("OrderList");
            cOrderDetailPanel.init();

        } else if (ae.getSource() == mainBtnManageMenuItem) {
            changeMainPanel("MenuManagement");

            cMenuManagementPanel.fill4();

        } else if (ae.getSource() == mainBtnShowRaport) {
            changeMainPanel("RaportPanel");

        } else if (ae.getSource() == headBtnLogout) {
            if (showYesNoDialog("Logout", "Are you sure to logout?") == DIALOG_YES) {

                cLoginPanel.setV();
                changeMainPanel("Login");
                headBtnLogout.setEnabled(false);

                headBtnLogin.setEnabled(true);
                changeMode(MODE_ANONIM);

            }
        } else if (ae.getSource() == headSaveR) {
            DeliverySerializer.serialize(restaurant);

        } else if (ae.getSource() == headBtnLogin) {
cLoginPanel.setCheck();
            changeMainPanel("Login");

            cLoginPanel.init();
        } }
        public void changeMainPanel(String panelName) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, panelName); }

    private class LoginPanel extends JPanel implements ActionListener {

        private JPanel loginPanel;
        private JLabel lblUserID;
        private JTextField tfUserID;
        private JLabel lblPassword;
        private JPasswordField pwPassword;
        private JCheckBox chbIsManager;
        private JButton btnLoginOK;

        public LoginPanel() {


            loginPanel = new JPanel();
            GridBagLayout gbLayout = new GridBagLayout();
            loginPanel.setLayout(gbLayout);
            GridBagConstraints gbc = new GridBagConstraints();
            lblUserID = new JLabel("UserID:");
            lblUserID.setPreferredSize(new Dimension(100, 30));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbLayout.setConstraints(lblUserID, gbc);
            loginPanel.add(lblUserID);
            tfUserID = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbLayout.setConstraints(tfUserID, gbc);
            loginPanel.add(tfUserID);
            lblPassword = new JLabel("Password:");
            lblPassword.setPreferredSize(new Dimension(100, 30));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbLayout.setConstraints(lblPassword, gbc);
            loginPanel.add(lblPassword);
            pwPassword = new JPasswordField(20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbLayout.setConstraints(pwPassword, gbc);
            loginPanel.add(pwPassword);
            chbIsManager = new JCheckBox("Login as admin");
            chbIsManager.setSelected(false);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(chbIsManager, gbc);
            loginPanel.add(chbIsManager);
            btnLoginOK = new JButton("Login");
            btnLoginOK.addActionListener(this);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbLayout.setConstraints(btnLoginOK, gbc);
            loginPanel.add(btnLoginOK);
            JLabel homeImage = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            homeImage.setIcon(new ImageIcon("image" + ".jpg"));
            loginPanel.add(homeImage, gbc);
            this.add(loginPanel);

        }
        private void setUserID(String id) {
            tfUserID.setText(id);
        }

        private void setPassword(String password) {
            pwPassword.setText(password);
        }

        public void init() {
            setUserID("");
            setPassword("");
            tfUserID.setBackground(UIManager.getColor("TextField.background"));
        }
public  void setCheck(){
    chbIsManager.setSelected(false);
}
        public void setV() {
            loginPanel.setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnLoginOK) {
                String id = tfUserID.getText();
                boolean isManager = chbIsManager.isSelected();
                if (id.equals("")) {
                    showErrorDialog("Error", "Enter ID!");
                    return;
                }
                char[] password = pwPassword.getPassword();
                String password1 = String.copyValueOf(password);
                if (password1.equals("")) {
                    showErrorDialog("Error", "Enter password!");
                    return;
                }
                if (controller.loginCheck(id, password1, isManager)) {
                    showConfirmDialog("Message", "Login success!!");
                    tfUserID.setText("");
                    pwPassword.setText("");
                    if (isManager) {
                        changeMode(MODE_ADMIN);
                    } else {
                        changeMode(MODE_CLIENT);
                    }
                    loginPanel.setVisible(false);
                    headBtnLogin.setEnabled(false);
                    headBtnLogout.setEnabled(true);
                } else {
                    showErrorDialog("Error", "Not found");
                }
            }
        }
    }
}