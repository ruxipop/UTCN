package com.bianca.dao;

import com.bianca.model.Cart;
import com.bianca.model.Flower;

import java.util.List;

public interface CartDAO {

    void insertCart(Cart cart);
    List<Cart> loadCarts();
    Cart getCartByCartItemAndUsername(int id, String username);
    List<Cart> getCartsByUsername(String username);
    void  updateCart(Cart cart);
    void deleteCart(int id, String username);
}