package controller;

import view.*;
import model.*;
import proxy.*;

import java.awt.event.*;
import javax.swing.*;

@lombok.Getter
@lombok.Setter
public class LoginC {


    private ModelAdmin modelAdmin;
    private LoginGUI loginGUI;

    public LoginC(ModelAdmin modelAdmin, LoginGUI loginGUI) {
        this.modelAdmin = modelAdmin;
        this.loginGUI = loginGUI;
        loginGUI.logIn(new LogIn());
        loginGUI.roB(new RoB());
        loginGUI.enB(new EnB());
        loginGUI.spB(new SpB());
    }

    class LogIn implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean ok = true;
            String username = loginGUI.getUsernameField().getText();
            if (username.equals("")) {
                ok = false;
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("enterUsername"));
                return;
            }
            String password = String.copyValueOf(loginGUI.getPasswordField().getPassword());

            if (password.equals("")) {
                ok = false;
                JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("enterPassword"));
                return;
            }
            if (ok) {
                User user = modelAdmin.getUserBLL().existUser(username, password);


                if (user == null) {
                    JOptionPane.showMessageDialog(null, modelAdmin.getLanguage().getTextLanguage().get("tryAgain"));
                    return;
                } else {

                    ProxyLogin proxy = new ProxyLogin(user, modelAdmin, loginGUI);
                    proxy.login();

                }


            }
        }

    }

    class RoB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelAdmin.getLanguage().setLanguage("romanian");
            modelAdmin.notify("language");

        }
    }

    class EnB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelAdmin.getLanguage().setLanguage("english");
            modelAdmin.notify("language");

        }
    }

    class SpB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelAdmin.getLanguage().setLanguage("spanish");
            modelAdmin.notify("language");

        }
    }
}
