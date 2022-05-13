package View;

import Presenter.LogInP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI implements ILoginView, ActionListener {
    public JPanel panel1;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    JFrame frame = new JFrame("Login page");
    private boolean error = false;


    public LoginGUI() {

        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);

        signInButton.addActionListener(this);


    }

    @Override
    public String getUsernameField() {

        if (usernameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Insert username!",
                    "Swing Tester", JOptionPane.ERROR_MESSAGE);
            error = true;
            return null;
        }

        return usernameField.getText();
    }

    @Override
    public void setUsernameField(String username) {
        usernameField.setText(username);
    }

    @Override
    public String getPasswordField() {

        if (passwordField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Insert password!",
                    "Swing Tester", JOptionPane.ERROR_MESSAGE);
            error = true;
            return null;
        }

        return passwordField.getText();
    }

    @Override
    public void setPassword(String password) {
        passwordField.setText(password);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signInButton) {
            logInB(ae);
        }
    }


    public JPanel getPanel1() {
        return panel1;

    }
    public void logInB(ActionEvent actionEvent) {


        LogInP logInP = new LogInP(this);
        int userRole = logInP.logIn();
        if (userRole == 1) {
            frame.setVisible(false);
            new UserGUI();

        } else if (userRole == 2) {
            frame.setVisible(false);
            new AdministratorGUI();
        } else if (!error) {
            JOptionPane.showMessageDialog(frame, "Password or username incorect!",
                    "Swing Tester", JOptionPane.ERROR_MESSAGE);
        }


    }
}
