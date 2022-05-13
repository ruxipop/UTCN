package Presenter;


import Model.User;
import Model.UserPersistance;
import View.IAdministratorView;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.List;

public class AdministratorP {
    private static IAdministratorView administratorView;
    private UserPersistance userPersistance;

    public AdministratorP(IAdministratorView administratorView) {
        this.userPersistance = new UserPersistance();
        this.administratorView = administratorView;

    }

    public List<User> getUsers(){


        return userPersistance.getUsersList();
    }
    public void deleteUser() {
        if (this.administratorView.selectie() != "") {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to delete?");
            // 0=yes, 1=no, 2=cancel
            if (input == 0) {
                boolean succes = this.userPersistance.deleteUser(this.administratorView.selectie());
                if (!succes) {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a user!");
            return;

        }
    }

    public void addUser() {



            if (!this.administratorView.getFirstName().isEmpty() && !this.administratorView.getLastName().isEmpty() && !this.administratorView.getUsername().isEmpty() && !this.administratorView.getPassword().isEmpty() && !this.administratorView.getPassword().isEmpty() && (!this.administratorView.adminSelect() || !this.administratorView.employeeSelect())) {
                boolean corect = true;
                String role;

                if (this.administratorView.adminSelect()) {
                    role = "admin";
                } else {
                    role = "employee";
                }
                User user = new User(this.administratorView.getFirstName(), this.administratorView.getLastName(), this.administratorView.getUsername(), this.administratorView.getPassword(), role);
                boolean exist = this.userPersistance.existUser(user);
                if (!exist) {

                    boolean succes = this.userPersistance.addUser(user);

                    if (!succes) {
                        JOptionPane.showMessageDialog(null, "Error");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Exist!");
                }
            }
         else{
        JOptionPane.showMessageDialog(null,"Complete all fields!");
        }



}

    public void updateUser() {
        if (this.administratorView.selectie() != "") {
            try {

                if (!this.administratorView.getFirstName().isEmpty() || !this.administratorView.getLastName().isEmpty() || !this.administratorView.getUsername().isEmpty() || !this.administratorView.getPassword().isEmpty() || !this.administratorView.getPassword().isEmpty() || (!this.administratorView.adminSelect() || !this.administratorView.employeeSelect())) {

                    boolean corect = true;
                    String role;

                    if (this.administratorView.adminSelect()) {
                        role = "admin";
                    } else if (this.administratorView.employeeSelect()) {
                        role = "employee";
                    } else {
                        role = "";
                    }

                    User user = new User(this.administratorView.getFirstName(), this.administratorView.getLastName(), this.administratorView.getUsername(), this.administratorView.getPassword(), role);

                    boolean succes = this.userPersistance.updateUser(user, this.administratorView.selectie());

                    if (!succes) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }


            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Complete all fields!");
            return;
        }
    }

    public void showUsersB(){
        DefaultTableModel model=this.administratorView.getModel();
        int rowCount = model.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for (User user : this.getUsers()) {

            Object[] o = new Object[5];

            o[0] = user.getFirstName();
            o[1] = user.getLastName();
            o[2] = user.getUsername();
            o[3] = user.getPassword();
            o[4] = user.getRole();

            model.addRow(o);

        }
        this.administratorView.setTable(model);

    }
}
