package Presenter;

import Model.User;
import Model.UserPersistance;
import View.ILoginView;

import java.awt.event.ActionEvent;
import java.util.List;

public class LogInP {
    private static ILoginView logInView;
    private UserPersistance userPersistance;


    public LogInP(ILoginView view) {
        this.userPersistance = new UserPersistance();
        this.logInView = view;
    }

    public int logIn() {
        userPersistance.readUser();
        List<User> usersList = userPersistance.getUsersList();
        try {
            for (User user : usersList) {


                if (this.logInView.getUsernameField().equals(user.getUsername())) {

                    if (logInView.getPasswordField().equals(user.getPassword())) {

                        if (user.getRole().equals("employee")) {

                            return 1;
                        } else {
                            return 2;
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
        return 0;

    }

}
