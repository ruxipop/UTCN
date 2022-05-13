package View;

import Model.User;
import Presenter.AdministratorP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorGUI implements IAdministratorView, ActionListener {
    private JTable showTable;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField username;
    private JPasswordField password;
    private JRadioButton adminRadioButton;
    private JRadioButton employeeRadioButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton showButton;
    private JButton logOutButton;
    private DefaultTableModel model;
    public JPanel panel;
    JFrame frame1 = new JFrame("Admin page");
    ButtonGroup g = new ButtonGroup();

    public AdministratorGUI() {
        model = new DefaultTableModel(new Object[0][0], new String[]{"First Name", "Last Name", "Username", "Password", "Role"});
        g.add(adminRadioButton);
        g.add(employeeRadioButton);
        frame1.add(panel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
        logOutButton.addActionListener(this);
        deleteButton.addActionListener(this);
        showButton.addActionListener(this);
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
    }
    @Override
 public DefaultTableModel getModel(){
        return this.model;
 }
    @Override
 public void setTable(DefaultTableModel model){
         this.showTable.setModel(model);
 }
    @Override
    public String getFirstName() {
        return firstName.getText();

    }

    @Override
    public void setFirstName(String firstName1) {
        firstName.setText(firstName1);

    }

    @Override
    public String getLastName() {
        return lastName.getText();
    }

    @Override
    public void setLastName(String lastName1) {
        lastName.setText(lastName1);
    }

    @Override
    public String getUsername() {
        return username.getText();

    }

    @Override
    public void setUsername(String username1) {
        username.setText(username1);
    }

    @Override
    public String getPassword() {
        return password.getText();
    }

    @Override
    public void setPassword(String password1) {
        password.setText(password1);
    }

    @Override
    public String selectie() {
        int row = showTable.getSelectedRow();
        if (row >= 0) {
            String value = showTable.getModel().getValueAt(row, 2).toString();
            return value;
        }
        return "";
    }

    @Override
    public boolean adminSelect() {
        return adminRadioButton.isSelected();

    }

    @Override

    public boolean employeeSelect() {
        return employeeRadioButton.isSelected();

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == logOutButton) {
            logOut(ae);
        }
        if (ae.getSource() == deleteButton) {
            deleteUser(ae);
        }
        if (ae.getSource() == showButton) {
            showUsers(ae);
        }
        if (ae.getSource() == addButton) {
            addUser(ae);
        }
        if (ae.getSource() == updateButton) {
            updateUserB(ae);
        }
    }

    public void logOut(ActionEvent actionEvent) {
        frame1.setVisible(false);
        new LoginGUI();

    }

    public void deleteUser(ActionEvent actionEvent) {
        AdministratorP administratorP = new AdministratorP(this);
        administratorP.deleteUser();

    }

    public void addUser(ActionEvent actionEvent) {
        AdministratorP administratorP = new AdministratorP(this);
        administratorP.addUser();

    }

    public void updateUserB(ActionEvent actionEvent) {
        AdministratorP administratorP = new AdministratorP(this);
        administratorP.updateUser();

    }


    private void showUsers(ActionEvent actionEvent) {



        AdministratorP administratorP = new AdministratorP(this);
        administratorP.showUsersB();
    }


}
