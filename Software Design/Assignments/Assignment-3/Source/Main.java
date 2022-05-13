import View.*;
import Model.*;

public class Main {
    public static void main(String[] args) {
        ModelAdmin modelAdmin = new ModelAdmin(new UserPersistance());
        LoginGUI loginGUI = new LoginGUI(modelAdmin);
        new Controller.LoginC(modelAdmin, loginGUI);
    }
}
