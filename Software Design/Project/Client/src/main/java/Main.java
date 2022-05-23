
import com.*;
import model.*;
import view.*;

import java.io.*;

public class Main {

    public static void main(String args[]) throws IOException, IllegalAccessException {
        Client client = new Client();
        client.connect();
        ModelAdmin modelAdmin = new model.ModelAdmin(new bll.UserBLL(client));
        LoginGUI loginGUI = new view.LoginGUI(modelAdmin);
        new controller.LoginC(modelAdmin, loginGUI);

    }
}
