package model;

/**
 * Clasa care implementeaza Model Classes pentru tabelul Product.
 */
public class Product {
    private int idProduct;
    private String nameProduct;
    private int quantity;

    private double price;

    public Product(int idProduct, int quantity, String nameProduct, double price) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.nameProduct = nameProduct;
        this.price = price;
    }

    public Product() {
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [idProduct = " + idProduct + "quantity" + quantity + "nameProduct" + nameProduct + "price" + price + "]";
    }
}