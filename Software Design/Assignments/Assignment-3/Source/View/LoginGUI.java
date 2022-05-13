package View;

import java.util.Map;
import javax.swing.*;
import java.awt.event.ActionListener;

import Model.*;

@lombok.Getter
@lombok.Setter
public class LoginGUI implements Observer {

    private ModelAdmin modelAdmin;
    public JPanel panel1;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    JFrame frame = new JFrame("Login page");


    public LoginGUI(ModelAdmin modelAdmin) {

        this.modelAdmin = modelAdmin;
        modelAdmin.add(this);
        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        button1.setBorder(BorderFactory.createEmptyBorder());
        button2.setBorder(BorderFactory.createEmptyBorder());
        button3.setBorder(BorderFactory.createEmptyBorder());
        changeLanguage();
    }


    public void logIn(ActionListener avl) {
        signInButton.addActionListener(avl);


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
        changeLanguage();
    }

    public void changeLanguage() {

        Map<String, String> language = this.modelAdmin.getLanguage().getTextLanguage();
        signInButton.setText(language.get("buttonLogin"));
        usernameLabel.setText("                             " + language.get("usernameLabel"));
        passwordLabel.setText("                             " + language.get("passwordLabel"));
        label1.setText(language.get("labelSpec"));
        label2.setText(language.get("labelPass"));
        label3.setText(language.get("labelSince"));

    }
}
