package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.awt.event.ActionListener;


@lombok.Getter
@lombok.Setter
public class AdministratorGUI implements Observer {
    private ModelAdmin modelAdmin;
    private Map<String, String> language;

    private JTextField firstName;

    private JTextField lastName;

    private JTextField username;

    private JPasswordField password;


    private JCheckBox Employee;


    private JCheckBox Admin;


    private ButtonGroup checkBoxGroup;

    private JButton updateButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton showButton;
    private JButton logOutButton;


    private JTable showTable;
    private DefaultTableModel model;
    public JPanel panel;
    private JScrollPane JScroll;
    private JComboBox comboBox1;
    private JButton filterButton;
    private JPanel logOut;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel usernameField;
    private JLabel passwordField;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel labelUser;
    private JLabel labelBest;


    JFrame frame1 = new JFrame("Admin page");


    public AdministratorGUI(ModelAdmin modelAdmin) {
        this.modelAdmin = modelAdmin;

        language = this.modelAdmin.getLanguage().getTextLanguage();
        modelAdmin.add(this);
        fillRole();
        checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(Admin);
        checkBoxGroup.add(Employee);

        frame1.add(panel);
        frame1.setPreferredSize(new java.awt.Dimension(900, 600));
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        this.modelAdmin.notify("show");
        this.modelAdmin.notify("language");

        frame1.setVisible(true);
        button1.setBorder(BorderFactory.createEmptyBorder());
        button2.setBorder(BorderFactory.createEmptyBorder());
        button3.setBorder(BorderFactory.createEmptyBorder());


    }

    public void removeCombo(JComboBox combo) {
        int number = combo.getItemCount();
        for (int i = 0; i < number; i++) {

            combo.removeItem(combo.getItemAt(0));


        }
    }

    public void fillRole() {
        removeCombo(comboBox1);
        comboBox1.addItem(language.get("buttonSelect"));

        for (String s : this.modelAdmin.getUserPersistance().typeOfRole()) {

            comboBox1.addItem(s);

        }

    }

    public String selectie() {
        int row = showTable.getSelectedRow();
        if (row >= 0) {
            String value = showTable.getModel().getValueAt(row, 2).toString();
            return value;
        }
        return "";
    }

    public void setTable(java.util.List<Model.User> list) {
        String first = modelAdmin.getLanguage().getTextLanguage().get("tableFirst");
        String last = modelAdmin.getLanguage().getTextLanguage().get("tableNume");
        String username = modelAdmin.getLanguage().getTextLanguage().get("tableUsername");
        String password = modelAdmin.getLanguage().getTextLanguage().get("tableParola");
        String role = modelAdmin.getLanguage().getTextLanguage().get("tableRol");


        model = new DefaultTableModel(new Object[0][0], new String[]{first, last, username, password, role});

        int rowCount = this.model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for (Model.User user : list) {

            Object[] o = new Object[5];

            o[0] = user.getFirstName();
            o[1] = user.getLastName();
            o[2] = user.getUsername();
            o[3] = user.getPassword();
            o[4] = user.getRole();

            model.addRow(o);

        }
        showTable.setModel(model);

    }

    public void reset() {
        firstName.setText("");
        lastName.setText("");
        password.setText("");
        username.setText("");
        checkBoxGroup.clearSelection();


    }

    public void logOut(ActionListener avl) {
        logOutButton.addActionListener(avl);


    }


    public void deleteUser(ActionListener avl) {
        deleteButton.addActionListener(avl);
    }


    public void addUser(ActionListener avl) {
        addButton.addActionListener(avl);
    }

    public void updateUser(ActionListener avl) {

        updateButton.addActionListener(avl);
    }

    public void filterUser(ActionListener avl) {
        filterButton.addActionListener(avl);
    }

    public void roB(ActionListener avl) {
        button1.addActionListener(avl);


    }

    public void enB(ActionListener avl) {
        button2.addActionListener(avl);


    }

    public void spB(ActionListener avl) {
        button3.addActionListener(avl);


    }

    @Override
    public void update(Object o) {
        String s = (String) o;
        switch (s) {
            case "language":
                changeLanguage();
                break;
            case "show":
                this.setTable(modelAdmin.getUserPersistance().getUsersList());
                this.fillRole();

        }
    }

    public void changeLanguage() {
        language = this.modelAdmin.getLanguage().getTextLanguage();

        logOutButton.setText(language.get("logOutButton"));
        firstNameLabel.setText(language.get("firstName"));
        lastNameLabel.setText(language.get("lastName"));
        passwordField.setText(language.get("passwordLabel1"));
        usernameField.setText(language.get("usernameLabel1"));
        Admin.setText(language.get("checkAdmin"));
        Employee.setText(language.get("checkEmployee"));
        filterButton.setText(language.get("buttonFilter"));
        addButton.setText(language.get("buttonAdaugare"));
        deleteButton.setText(language.get("buttonStergere"));
        updateButton.setText(language.get("buttonActualizare"));
        labelBest.setText(language.get("labelBest"));
        labelUser.setText(language.get("labelUser"));
        fillRole();

    }
}
