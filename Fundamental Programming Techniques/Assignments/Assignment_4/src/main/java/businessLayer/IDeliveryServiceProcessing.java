package businessLayer;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IDeliveryServiceProcessing {
    /**
     * Creates and adds a new MenuItem.
     *@param item the new item to be added.
     * @pre item != null.
     * @invariant isWellFormed().
     */
    void createMenuItem(MenuItem item);

    /**
     * Creates and adds a new composite Product.
     *
     * @param name the new composite product to be added to menu.
     * @pre name != null.
     * @invariant isWellFormed().
     */

    void createMenuItem(String name);


    /**
     * Adds a Base product to the an  existing composite Product.
     *
     * @param compositeItem name of the composite product.
     * @param name          the name of the base product.
     * @pre compositeItem != null && name != null.
     * @pre menuItems.get(name) != null && menuItems.get(compositeItem ) != null.
     * @post (( CompositeProduct)menuItems.get(compositeItem)).contains(menuItems.get(name)).
     */


    void addItemToComponent(String compositeItem, String name);

    /**
     * Removes a base product from a composite product.
     *
     * @param baseName      the base product that  will be removed.
     * @param componentName the name of the component.
     * @pre assert baseName != null && componentName != null.
     * @post (( CompositeProduct)menuItems.get(componentName)).contains(menuItems.get(baseName)).
     */
    void removeItemFromComponent(String baseName, String componentName);

    /**
     * Edit the price of a base product.
     *
     * @param item  name of the base product.
     * @param price the new price of the item.
     * @pre item != null && price <=-1 .
     * @invariant isWellFormed().
     */
    void editMenuItem(BaseProduct item, float price);


    /**
     * Edit the name of a composite product.
     *
     * @param item name of the composite product.
     * @param name the new name of the item.
     * @pre assert item != null && name != null .
     * @invariant isWellFormed().
     */
    void editMenuItem(CompositeProduct item, String name);


    /**
     * Deletes a menu item from the list.
     *
     * @param nameItem name of the item to be deleted.
     * @pre name != null.
     * @invariant isWellFormed().
     */

    void deleteMenuItem(String nameItem);

    /**
     * Creates an order,and add it to a order list ,and create create a map for order who will be populated with MenuItem.
     *
     * @param date date when the client order.
     * @pre date != null.
     */
    void createOrder(Date date);

    /**
     * Adds item to a order with orderID.
     *
     * @param orderID  is id of the order.
     * @param menuItem name of the item which will be added to the order.
     * @pre menuItems.get(menuItem) != null && orderID >=0 && orderID < orders.size().
     */
    void addItemToOrder(int orderID, String menuItem);

    /**
     * Computes the price for the order with the orderID.
     *
     * @param orderID is id of the order.
     * @return price of the order.
     * @pre orderID >=0 && orderID < orders.size() && menuItems.get(menuItem) != null.
     */
    float computeOrderPrice(int orderID);

    /**
     * Generates a txt bill of the order with orderID.
     *
     * @param orderID is id of the order.
     * @throws IOException
     * @pre orderID >=0 && orderID < orders.size().
     */

    void generateBill(int orderID) throws IOException;


    /**
     * Metoda testează dacă structura meniului rămâne bine formată, adică fiecare element de meniu este
     * stocat în hashmap ca o pereche String
     *
     * @return true dacă clasa este bine formată
     */
    boolean isWellFormed();

    Map<Order, List<MenuItem>> getOrderList();

    List<Order> getOrders();

    Map<String, MenuItem> getMenuItems();


}
