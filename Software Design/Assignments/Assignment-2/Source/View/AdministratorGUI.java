package View;

import ViewModel.VMAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import net.sds.mvvm.bindings.*;

public class AdministratorGUI  implements ActionListener {
    private  VMAdmin vmAdmin;
    @Bind(value="text", target = "firstName.value")
    private JTextField firstName;
    @Bind(value="text", target = "lastName.value")
    private JTextField lastName;
    @Bind(value="text", target = "username.value")
    private JTextField username;
    @Bind(value="text", target = "password.value")
    private JPasswordField password;

    @Bind(value="selected", target = "Employee.value")

    private JCheckBox Employee;


    @Bind(value="selected", target = "Admin.value")
    private JCheckBox Admin;


    private ButtonGroup checkBoxGroup;

    private JButton updateButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton showButton;
    private JButton logOutButton;

    @Bind(value = "model", target = "model.value", type = BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedRow", target = "row.value", type = BindingType.BI_DIRECTIONAL)

    private JTable showTable;
    private DefaultTableModel model;
    public JPanel panel;
    private JScrollPane JScroll;


    JFrame frame1 = new JFrame("Admin page");


    public AdministratorGUI() {
        model = new DefaultTableModel(new Object[0][0], new String[]{"First Name", "Last Name", "Username", "Password", "Role"});

        Binding binding;

        this.vmAdmin=new VMAdmin();
        try {
            Binder.bind(this, vmAdmin);

        }catch(Exception e){

        }


        checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(Admin);
        checkBoxGroup.add(Employee);

        frame1.add(panel);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
        logOutButton.addActionListener(this);
        deleteButton.addActionListener(this);
        showButton.addActionListener(this);
        addButton.addActionListener(this);
        updateButton.addActionListener(this);

        firstName.addComponentListener(new ComponentAdapter() {
        });

    }







    @lombok.SneakyThrows
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

    public void deleteUser(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {
        vmAdmin.getDeleteUserCommand().execute(1);

    }

    public void addUser(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {
        vmAdmin.getAddUserCommand().execute(1);

    }

    public void updateUserB(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {
        vmAdmin.getUpdateUserCommand().execute(1);

    }


    private void showUsers(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {
        vmAdmin.getShowUsersCommand().execute(1);
    }


}
