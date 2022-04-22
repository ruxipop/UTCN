
package data;
import model.Model;
import view.View;
import controller.Controller;


public class Main {
    public static void main(String[] args) {

        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(view, model);
        view.setVisible(true);


    }
}