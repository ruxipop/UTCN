package businessLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private final int orderID;
    private final Date date;
    private float price;
    private int idC;

    public Order(int orderID, int idC, Date date, float price) {
        this.idC = idC;
        this.orderID = orderID;
        this.date = date;
        this.price = price;

    }

    public int getIdC() {
        return idC;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.hashCode() == o.hashCode()) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return
                idC == order.idC && orderID == order.orderID &&
                        date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, date);
    }

    @Override
    public String toString() {
        return "Order id: " + getOrderID() + "\n" +
                "Order date:" + date;
    }
}
