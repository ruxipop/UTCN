package com.bianca.dao;

import com.bianca.model.Flower;
import com.bianca.model.Wish;

import java.util.List;

public interface WishDAO {
    List<Wish> loadWishs();
    void insertWish(Wish wish);
    Wish getWishByIdAndUsername(int id, String username);
    void deleteWish(int id, String username);
}