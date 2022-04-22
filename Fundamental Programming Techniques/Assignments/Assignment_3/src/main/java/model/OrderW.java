package model;

/**
 * Clasa care implementeaza Model Classes pentru tabelul OrderW.
 */
public class OrderW {
    private int idOrder;
    private int idClient;
    private int idProduct;

    private int quantity;
    private double total;

    public OrderW(int idOrder, int idProduct, int idClient, int quantity, double total) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.idClient = idClient;
        this.quantity = quantity;
        this.total = total;
    }

    public OrderW() {

    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setNameProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {

        return "Order [id= " + idOrder + ", name = " + idClient + ", product = " + idProduct + ", quantity = " + quantity + ", price = " + total + "]";
    }
}

