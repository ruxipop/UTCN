package dataLayer;

import businessLayer.MenuItem;
import businessLayer.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextFileWriter {
    private static final boolean append = false;

    public static void generateBill(Order order, List<MenuItem> items, float price) throws IOException {
        File bill = new File("bill" + order.getOrderID() + ".txt");
        bill.createNewFile();

        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("Order details :%n");
        printWriter.printf("%s%n", order.toString());
        printWriter.printf("Products details :%n");
        for (MenuItem menuItem : items) {
            printWriter.printf("%s%n", menuItem.getName() + " " + menuItem.computePrice());
        }
        printWriter.printf("%s%n", "Total " + price + " $");
        printWriter.close();
    }

    public static void generateRaport1(List<Order> orders) throws IOException {
        File bill = new File("raport1" + ".txt");
        bill.createNewFile();
        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Order o : orders) {
            printWriter.printf("%s%n", o.toString());


        }
        printWriter.close();
    }

    public static void generateRaport2(List<String> items) throws IOException {
        File bill = new File("raport2" + ".txt");
        bill.createNewFile();
        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (String s : items) {
            printWriter.printf("%s%n", s);
        }
        printWriter.close();
    }

    public static void generateRaport3(List<Integer> items) throws IOException {
        File bill = new File("raport3" + ".txt");
        bill.createNewFile();
        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (int i = 0; i < items.size(); i++) {

            printWriter.printf("Clientul cu id %d %n", items.get(i));


        }
        printWriter.close();
    }

    public static void generateRaport4(Map<String, Long> lista) throws IOException {
        File bill = new File("raport4" + ".txt");
        bill.createNewFile();
        FileWriter fileWriter = new FileWriter(bill.getCanonicalPath(), append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<String> products = new ArrayList<>(lista.keySet());
        List<Long> no = new ArrayList<>(lista.values());
        for (int i = 0; i < lista.size(); i++) {

            printWriter.printf(" %s %n", products.get(i) + " " + no.get(i).toString());


        }
        printWriter.close();
    }

}
