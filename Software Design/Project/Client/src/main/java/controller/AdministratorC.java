package controller;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import view.*;


public class AdministratorC {
    private ModelAdmin modelAdmin;
    private AdministratorGUI administratorGUI;

    public AdministratorC(AdministratorGUI administratorGUI, ModelAdmin modelAdmin) throws IOException {

        this.administratorGUI = administratorGUI;
        this.modelAdmin = modelAdmin;

        administratorGUI.deleteUser(new DeleteUser());
        administratorGUI.addUser(new AddUser());
        administratorGUI.updateUser(new UpdateUser());
        administratorGUI.logOut(new LogOut());
        administratorGUI.filterUser(new FilterUsers());
        administratorGUI.enB(new EnB());
        administratorGUI.roB(new RoB());
        administratorGUI.spB(new SpB());
    }

    class RoB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            modelAdmin.getLanguage().setLanguage("romanian");
            modelAdmin.notify("language");
            modelAdmin.notify("show");


        }
    }

    class EnB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            modelAdmin.getLanguage().setLanguage("english");
            modelAdmin.notify("language");
            modelAdmin.notify("show");


        }
    }

    class SpB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            modelAdmin.getLanguage().setLanguage("spanish");
            modelAdmin.notify("language");
            modelAdmin.notify("show");


        }
    }

    class LogOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            administratorGUI.getFrame1().setVisible(false);
            new LoginC(modelAdmin, new view.LoginGUI(modelAdmin));
        }
    }


    class DeleteUser implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (administratorGUI.selectie() != "") {
                int input = JOptionPane.showConfirmDialog(null, modelAdmin.getLanguage().getTextLanguage().get("deleteUser"));
                // 0=yes, 1=no, 2=cancel
                if (input == 0) {
                    modelAdmin.getUserBLL().deleteUser(administratorGUI.selectie());

                }
                modelAdmin.notify("show");
            } else {
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("selectUser"));
                return;
            }
        }

    }

    class AddUser implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            if (administratorGUI.getAdmin().isSelected() || administratorGUI.getEmployee().isSelected()) {
                String firstName = administratorGUI.getFirstName().getText();
                String lastName = administratorGUI.getLastName().getText();
                String username = administratorGUI.getUsername().getText();
                String password = String.copyValueOf(administratorGUI.getPassword().getPassword());
                String role = administratorGUI.getAdmin().isSelected() ? "Admin" : "Employee";

                if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if (!username.isEmpty()) {
                        User user = null;
                        for (int i = 0; i < modelAdmin.getUserBLL().getUsersList().size(); i++) {
                            if (modelAdmin.getUserBLL().getUsersList().get(i).getUsername().equals(username))
                                user = modelAdmin.getUserBLL().getUsersList().get(i);

                        }
                        if (user != null) {
                            JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("userExist"));
                            return;
                        }
                    }
                    User user = new UserBuilder().firstName(firstName).lastName(lastName).password(password).username(username).role(role).build();
                    modelAdmin.getUserBLL().insertUser(user);


                } else {
                    JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("completeAll"));
                    return;
                }
                administratorGUI.reset();
                modelAdmin.notify("show");
            } else {
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("selectType"));
                return;
            }
        }

    }

    class UpdateUser implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (administratorGUI.selectie() != "") {
                String firstName = administratorGUI.getFirstName().getText();
                String lastName = administratorGUI.getLastName().getText();
                String username = administratorGUI.getUsername().getText();
                String password = String.copyValueOf(administratorGUI.getPassword().getPassword());
                String role = "";
                if (administratorGUI.getAdmin().isSelected()) {
                    role = "Admin";
                }
                if (administratorGUI.getEmployee().isSelected()) {
                    role = "Employee";
                }
                if (!firstName.equals("") || !lastName.equals("") || !password.equals("") || !role.equals("")) {

                    User user = null;
                    for (int i = 0; i < modelAdmin.getUserBLL().getUsersList().size(); i++) {
                        if (modelAdmin.getUserBLL().getUsersList().get(i).getUsername().equals(administratorGUI.selectie()))
                            user = modelAdmin.getUserBLL().getUsersList().get(i);
                    }
                    user.setPassword(password.isEmpty() ? user.getPassword() : password);
                    user.setFirstName(firstName.isEmpty() ? user.getFirstName() : firstName);
                    user.setLastName(lastName.isEmpty() ? user.getLastName() : lastName);
                    user.setRole(role.isEmpty() ? user.getRole() : role);
                    modelAdmin.getUserBLL().updateUser(user);


                } else {
                    if (!username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("notUsername"));
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("nothingUpdate"));
                        return;
                    }
                }
                administratorGUI.reset();
                modelAdmin.notify("show");
            } else {
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("selectUser"));
                return;
            }
        }
    }

    class FilterUsers implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = administratorGUI.getComboBox1().getSelectedItem().toString();

            if (!role.equals(modelAdmin.getLanguage().getTextLanguage().get("buttonSelect"))) {
                List<User> userToShow = modelAdmin.getUserBLL().filterUsers(role);

                if (userToShow.isEmpty()) {
                    JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("notExist"));
                    return;
                } else {
                    administratorGUI.setTable(userToShow);
                }
            } else {
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("selectCri"));
                return;
            }
        }
    }

}
