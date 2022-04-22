package businessLayer;

import dataLayer.TextFileWriter;
import presentationLayer.Controller;
import presentationLayer.EmployeeGUI;


import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable {
    private int orderID = 0;
    private final Observer observer;
    private Map<String, MenuItem> menuItems;
    private List<Order> orders;
    private Map<Order, List<MenuItem>> orderList;


    public DeliveryService() {
        menuItems = new HashMap<>();
        orders = new ArrayList<>();
        orderList = new HashMap<>();
        this.observer = new EmployeeGUI();
        assert this.observer != null;
    }


    @Override
    public void createMenuItem(MenuItem item) {
        assert item != null;
        menuItems.put(item.getName(), item);
        assert isWellFormed();
    }

    @Override
    public void createMenuItem(String name) {
        assert name != null;
        menuItems.put(name, new CompositeProduct(name));
        assert isWellFormed();
    }


    @Override
    public void addItemToComponent(String compositeItem, String name) {
        assert compositeItem != null && name != null;
        ((CompositeProduct) menuItems.get(compositeItem)).addItems((BaseProduct) menuItems.get(name));
        assert ((CompositeProduct) menuItems.get(compositeItem)).contains(menuItems.get(name));
    }

    @Override
    public void removeItemFromComponent(String componentName, String baseName) {
        assert baseName != null && componentName != null;
        ((CompositeProduct) menuItems.get(componentName)).getItems().remove(menuItems.get(baseName));
        assert !((CompositeProduct) menuItems.get(componentName)).contains(menuItems.get(baseName));
    }

    @Override
    public void editMenuItem(BaseProduct item, float price) {
        assert item != null && price <= -1;
        item.setPrice(price);
        assert isWellFormed();

    }

    @Override
    public void editMenuItem(CompositeProduct item, String name) {
        assert item != null && name != null;
        item.setName(name);
        assert isWellFormed();
    }


    @Override
    public void deleteMenuItem(String nameItem) {
        assert nameItem != null;
        MenuItem removed = menuItems.remove(nameItem);
        MenuItem product;
        for (int i = 0; i < menuItems.size(); i++) {
            product = menuItems.get(i);
            if (product instanceof CompositeProduct) {
                if (((CompositeProduct) product).contains(removed))
                    menuItems.remove(removed);

            }
        }

        assert isWellFormed();
    }

    @Override
    public void createOrder(Date date) {
        assert date != null;
        Order order = new Order(orderID, Controller.getID(), date, computeOrderPrice(orderID));
        orderID++;
        orderList.put(order, new ArrayList<>());
        orders.add(order);


    }

    @Override
    public void addItemToOrder(int orderID, String menuItem) {
        assert menuItems.get(menuItem) != null && orderID >= 0 && orderID < orders.size();
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                MenuItem item = menuItems.get(menuItem);

                orderList.get(order).add(item);
                order.setPrice(computeOrderPrice(orderID));
            }
        }
    }

    @Override
    public float computeOrderPrice(int orderID) {
        assert orderID < orders.size() && orderID >= 0;
        float price = 0;
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                for (MenuItem menuItem : orderList.get(order)) {
                    price += menuItem.computePrice();
                }

            }
        }
        return price;
    }

    @Override
    public boolean isWellFormed() {

        if (menuItems == null || orders == null) {
            return false;
        }


        for (int i = 0; i < menuItems.size(); i++) {
            for (MenuItem menu : menuItems.values()) {
                if (!menu.equals(menuItems.get(i))) {
                    return false;
                }
            }
        }


        return true;
    }


    @Override
    public void generateBill(int orderID) throws IOException {
        Order order = orders.get(orderID);
        TextFileWriter.generateBill(order, orderList.get(order), order.getPrice());
        orders.remove(orderID);
    }

    public void notifyObserver(Order order) {
        observer.update(this, order);
    }

    public Map<String, MenuItem> getMenuItems() {
        return menuItems;
    }

    public Map<Order, List<MenuItem>> getOrderList() {
        return orderList;
    }

    public List<Order> getOrders() {
        return orders;
    }

}
