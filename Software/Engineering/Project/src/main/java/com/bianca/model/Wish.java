package com.bianca.model;

public class Wish {
    private int wishId;
    private int wishItem;
    private String username;

    public Wish() {
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getWishItem() {
        return wishItem;
    }

    public void setWishItem(int wishItem) {
        this.wishItem = wishItem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "wishId=" + wishId +
                ", wishItem=" + wishItem +
                ", username=" + username +
                '}';
    }
}