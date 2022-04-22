import businessLayer.DeliveryService;

import dataLayer.DeliverySerializer;

import presentationLayer.Controller;

import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {

       DeliveryService delivery = new DeliveryService();
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Do you wanna reaload data for restaurant ?",
                "Choose",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            delivery= DeliverySerializer.deserialize("delivery.txt");
        }


    Controller controller=new Controller(delivery);

        presentationLayer.AdminClientGUI admin = new  presentationLayer.AdminClientGUI(delivery,controller);
        admin.setVisible(true);
    }
}

