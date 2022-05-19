package proxy;

import model.*;
import view.*;
import controller.*;

public class ProxyLogin implements Proxy {
    private User user;
    private ModelAdmin modelAdmin;
    private LoginGUI loginGUI;

    public ProxyLogin(User user, ModelAdmin modelAdmin, LoginGUI loginGUI) {
        this.user = user;
        this.modelAdmin = modelAdmin;
        this.loginGUI = loginGUI;
    }

    @Override
    public void login() throws Exception {
        String role = user.getRole();
        if (role.equals("Admin")) {


            view.AdministratorGUI administratorGUI = new view.AdministratorGUI(modelAdmin);
            new controller.AdministratorC(administratorGUI, modelAdmin);

        } else if (role.equals("Employee")) {

            ModelUser modelUser = new ModelUser(new bll.MovieBLL(modelAdmin.getUserBLL().getClient()));
            modelUser.getLanguage().setLanguage(modelAdmin.getLanguage().getLanguage());
            view.UserGUI userGUI = new view.UserGUI(modelUser, modelAdmin);
            new UserC(userGUI, modelUser);
        }
        loginGUI.getFrame().setVisible(false);
    }
}
