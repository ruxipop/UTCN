package com.bianca.model;

public class Cart {

    private int cartId;
    private int cartItem;
    private String username;
    private int quantity;
    public Cart(){



    }
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCartItem() {
        return cartItem;
    }

    public void setCartItem(int cartItem) {
        this.cartItem = cartItem;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", cartItem=" + cartItem +
                ", username=" + username +
                '}';
    }
}