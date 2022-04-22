package dataLayer;

import businessLayer.DeliveryService;
import businessLayer.IDeliveryServiceProcessing;


import java.io.*;


public class DeliverySerializer {

    public static void serialize(IDeliveryServiceProcessing app) {
        try {
            FileOutputStream file = new FileOutputStream("delivery.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(app);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DeliveryService deserialize(String path) {

        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            return (DeliveryService) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
