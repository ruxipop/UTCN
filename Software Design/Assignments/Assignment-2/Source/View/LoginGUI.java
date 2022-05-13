package View;

import ViewModel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sds.mvvm.bindings.*;

public class LoginGUI implements ActionListener {
    private VMLogin vmLogin;

    public JPanel panel1;
    @Bind(value = "text", target = "usernameField.value")
    private JTextField usernameField;
    @Bind(value = "text", target = "passwordField.value")
    private JPasswordField passwordField;
    private JButton signInButton;

    JFrame frame = new JFrame("Login page");



    public LoginGUI() {

        this.vmLogin = new VMLogin();
        try {
            Binder.bind(this, vmLogin);

        } catch (Exception e) {

        }

        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        signInButton.addActionListener(this);


    }


    @lombok.SneakyThrows
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signInButton) {
            logInB(ae);
        }
    }


    public void logInB(ActionEvent actionEvent) throws java.sql.SQLException {
        try {
            vmLogin.getLoginCommand().execute(1);

            frame.dispose();
        } catch (java.sql.SQLException ex) {


        }


    }


}
