package presentationLayer;

import businessLayer.*;
import businessLayer.MenuItem;
import dataLayer.TextStream;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private final static String PRODUCTS_FILE = "products.csv";
    private final static String MANAGER_FILE = "manage.txt";
    private final static String CLIENT_FILE = "client.txt";
    private static String ID;

    private DeliveryService restaurant;
    private AdminClientGUI view;

    public Controller(DeliveryService restaurant) {
        this.restaurant = restaurant;
        view = new AdminClientGUI(restaurant, this);
    }

    public static int getID() {
        return Integer.parseInt(ID);
    }


    public void createALLProducts() {
        List<MenuItem> m = TextStream.parseTextFile(PRODUCTS_FILE);

        for (int i = 0; i < m.size(); i++) {
            restaurant.createMenuItem(m.get(i));

        }
    }

    public boolean loginCheck(String inputID, String inputPassword, boolean isManager) {
        boolean ok = false;

        if (isManager == true) {
            List<String> manager = TextStream.parseTextAngajat(MANAGER_FILE);
            for (int i = 0; i < manager.size(); i++) {
                String manager1 = manager.get(i);
                String[] parts = manager1.split(" ");
                String id = parts[0].trim();
                String password = parts[1].trim();
                if (id.equals(inputID) && password.equals(inputPassword)) {
                    ID = id;
                    ok = true;
                }
            }


        } else {
            List<String> client = TextStream.parseTextAngajat(CLIENT_FILE);
            for (int i = 0; i < client.size(); i++) {
                String client1 = client.get(i);
                String[] parts = client1.split(" ");
                String id = parts[0].trim();
                String password = parts[1].trim();
                if (id.equals(inputID) && password.equals(inputPassword)) {
                    ID = id;
                    ok = true;
                }
            }


        }
        return ok;


    }

    public DefaultTableModel setMenu(int tip) {

        String[] columns = new String[]{"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        int noRows = restaurant.getMenuItems().size();
        String[][] data = new String[noRows][7];

        int rowIndex = 0;
        for (MenuItem item : restaurant.getMenuItems().values()) {
            if (tip == 0) {
                data[rowIndex][0] = item.getName();
                data[rowIndex][1] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getRating()) : "-";
                data[rowIndex][2] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getCalories()) : "-";
                data[rowIndex][3] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getProtein()) : "-";
                data[rowIndex][4] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getFat()) : "-";
                data[rowIndex][5] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getSodium()) : "-";


                data[rowIndex][6] = "" + item.computePrice();
                rowIndex++;
            }
            if (tip == 1 && item instanceof CompositeProduct) {
                data[rowIndex][0] = item.getName();
                data[rowIndex][1] = "-";
                data[rowIndex][2] = "-";
                data[rowIndex][3] = "-";
                data[rowIndex][4] = "-";
                data[rowIndex][5] = "-";


                data[rowIndex][6] = "" + item.computePrice();
                rowIndex++;


            }
            if (tip == 2 && item instanceof BaseProduct) {
                data[rowIndex][0] = item.getName();
                data[rowIndex][1] = String.valueOf(((BaseProduct) item).getRating());
                data[rowIndex][2] = String.valueOf(((BaseProduct) item).getCalories());
                data[rowIndex][3] = String.valueOf(((BaseProduct) item).getProtein());
                data[rowIndex][4] = String.valueOf(((BaseProduct) item).getFat());
                data[rowIndex][5] = String.valueOf(((BaseProduct) item).getSodium());


                data[rowIndex][6] = "" + item.computePrice();
                rowIndex++;
            }
            if (tip == 3) {

                for (int j = 0; j < 7; j++) {
                    data[0][j] = "";
                    return new DefaultTableModel(data, columns);
                }
            }
        }
        DefaultTableModel t = new DefaultTableModel(data, columns);

        return t;

    }


    public DefaultTableModel setMenuOrder(int tip) {

        String[] columns = new String[]{"Title", "Price"};
        int noRows = restaurant.getMenuItems().size();
        String[][] data = new String[noRows][2];

        int rowIndex = 0;
        for (MenuItem item : restaurant.getMenuItems().values()) {
            if (tip == 0) {
                data[rowIndex][0] = item.getName();

                data[rowIndex][1] = "" + item.computePrice();
                rowIndex++;
            }
            if (tip == 1 && item instanceof CompositeProduct) {
                data[rowIndex][0] = item.getName();

                data[rowIndex][1] = "" + item.computePrice();
                rowIndex++;


            }
            if (tip == 2 && item instanceof BaseProduct) {
                data[rowIndex][0] = item.getName();


                data[rowIndex][1] = "" + item.computePrice();
                rowIndex++;
            }
            if (tip == 3) {

                for (int j = 0; j < 2; j++) {
                    data[0][j] = "";
                    return new DefaultTableModel(data, columns);
                }
            }
        }
        DefaultTableModel t = new DefaultTableModel(data, columns);

        return t;

    }

    public DefaultTableModel setOrder() {
        String[] columns = new String[]{"Order ID", "Clinet Id", "Date", "Price"};
        String[][] data;
        if (restaurant.getOrders().isEmpty()) {
            data = new String[1][1];
            data[0][0] = "No Order";
        } else {


            int noRows = restaurant.getOrders().size();

            data = new String[noRows][4];
            int rowIndex = 0;

            for (Order order : restaurant.getOrders()) {
                data[rowIndex][0] = "" + order.getOrderID();
                data[rowIndex][1] = "" + order.getIdC();
                data[rowIndex][2] = order.getDate().toString();
                data[rowIndex][3] = "" + restaurant.computeOrderPrice(order.getOrderID());
                rowIndex++;

            }
        }
        DefaultTableModel t = new DefaultTableModel(data, columns);
        return t;

    }

    public DefaultTableModel setMenuSearch(List<MenuItem> m) {
        String[] columns = new String[]{"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        String[][] data;


        int noRows = m.size();

        data = new String[noRows][7];
        int rowIndex = 0;

        for (MenuItem item : m) {
            data[rowIndex][0] = item.getName();
            data[rowIndex][1] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getRating()) : "-";
            data[rowIndex][2] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getCalories()) : "-";
            data[rowIndex][3] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getProtein()) : "-";
            data[rowIndex][4] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getFat()) : "-";
            data[rowIndex][5] = (item instanceof BaseProduct) ? String.valueOf(((BaseProduct) item).getSodium()) : "-";


            data[rowIndex][6] = "" + item.computePrice();
            rowIndex++;


        }
        DefaultTableModel t = new DefaultTableModel(data, columns);
        return t;

    }

    public ArrayList<String> createOrderItemlList(int orderID) {
        ArrayList<String> initData = new ArrayList<String>();
        if (orderID == -1) {
            initData.add("");
        } else {
            List<MenuItem> orderListItem = new ArrayList<>();
            for (Order order : restaurant.getOrders()) {
                if (order.getOrderID() == orderID) {
                    orderListItem = restaurant.getOrderList().get(order);

                }
            }


            String output;

            Iterator<MenuItem> it = orderListItem.listIterator();
            MenuItem re;

            int count = 0;

            while (it.hasNext()) {
                re = it.next();
                output = String.format("%-4d|%-24s|%5.2f",
                        ++count, re.getName(), re.computePrice());
                initData.add(output);
            }
            if (initData.isEmpty())
                initData.add("");


        }
        return initData;
    }


    public List<MenuItem> findItemAll(final List<MenuItem> list, final float rating, final String title, final float calories, final float protein, final float fat, final float price, final float sodium) {

        return list.stream().filter(p -> {
            return (title.equals("") || p.getName().contains(title)) && ((rating == -1) || p.getRating() == rating) && ((calories == -1) || p.getCalories() == calories) && (protein == -1 || p.getProtein() == protein) && (fat == -1 || p.getFat() == fat)
                    && (price == -1 || p.computePrice() == price) && (sodium == -1 || p.getSodium() == sodium);

        }).collect(Collectors.toList());

    }

    public Map<Integer, Long> findRaport3(final List<Order> list, final int value) {
        return list.stream().filter(p -> p.getPrice() > value).collect(Collectors.groupingBy(p -> p.getIdC(), Collectors.counting()));

    }
    public Map<String, Long> findRaport4(final LocalDate l) {
        return getRestaurant().getOrderList().entrySet().stream().filter(p -> p.getKey().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() == l.getYear() && p.getKey().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth() == l.getDayOfMonth() && p.getKey().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() == l.getMonthValue()).map(p -> p.getValue()).flatMap(Collection::stream).collect(Collectors.groupingBy(MenuItem::getName, Collectors.counting()));
    }

    public Map<String, Long> findRaport2() {
        return getRestaurant().getOrderList().entrySet().stream().map(p -> p.getValue()).flatMap(Collection::stream).collect(Collectors.groupingBy(MenuItem::getName, Collectors.counting()));
    }
    public DeliveryService getRestaurant() {
        return this.restaurant;
    }
}
