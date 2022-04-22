package com.bianca.dao;

import com.bianca.mappers.WishRowMapper;
import com.bianca.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class WishDAOImpl implements WishDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Wish> loadWishs() {

        String sql = "SELECT * FROM wish";

        return jdbcTemplate.query(sql, new WishRowMapper());
    }
    @Override
    public void insertWish(Wish wish)  {

        boolean t=false;

        List<Wish> wishList = loadWishs();

        for(Wish w:wishList) {
            if (w.getWishItem() == wish.getWishItem() && w.getUsername().equals(wish.getUsername())) {
                t=true;
            }
        }
        if(t){

        }
        else{


            Object[] sqlArgs = {wish.getWishItem(), wish.getUsername()};
            String sql = "INSERT INTO wish (wishItem, username) VALUES (?, ?)";
            jdbcTemplate.update(sql, sqlArgs);
        }


    }
    @Override
    public Wish getWishByIdAndUsername(int id, String username) {
        Object[] sqlArgs = {id, username};
        String sql = "SELECT * FROM wish WHERE wishItem = ? and username = ?";

        return jdbcTemplate.queryForObject(sql, new WishRowMapper(), sqlArgs);
    }

    @Override
    public void deleteWish(int id, String username) {
        String sql = "DELETE FROM wish WHERE wishItem = " + id + " and username = '" + username + "'";
        jdbcTemplate.update(sql);
    }



}