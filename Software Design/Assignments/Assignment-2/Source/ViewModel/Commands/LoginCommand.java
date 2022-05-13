package ViewModel.Commands;

import ViewModel.*;
import Model.*;
import java.util.*;

public class LoginCommand implements ICommand {
    private VMLogin vmLogin;
    public LoginCommand(ViewModel.VMLogin vmLogin) {
        this.vmLogin = vmLogin;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        boolean t = false;
        if (!vmLogin.getPasswordField().get().isEmpty() && !vmLogin.getUsernameField().get().isEmpty()) {
            UserPersistance per = new Model.UserPersistance();
            List<Model.User> usersList = per.getUsersList();
            for (User user : usersList) {
                if (vmLogin.getPasswordField().get().equals(user.getPassword())) {
                    if (vmLogin.getUsernameField().get().equals(user.getUsername())) {
                        t = true;
                        if (user.getRole().equals("Employee")) {
                            new View.UserGUI();
                        } else {
                            new View.AdministratorGUI();
                        }
                    }
                }
            }
            if (!t) {
                javax.swing.JOptionPane.showMessageDialog(null, "Doesn't exists! Incorrect data! Try again");
                throw new java.sql.SQLException("exception");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Complete all fields!");
            throw new java.sql.SQLException("exception");
        }
    }
}
