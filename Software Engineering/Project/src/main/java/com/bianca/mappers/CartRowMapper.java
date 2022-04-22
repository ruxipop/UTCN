package com.bianca.mappers;

import com.bianca.model.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {

        Cart c = new Cart();
        c.setCartId(rs.getInt("cartId"));
        c.setCartItem(rs.getInt("cartItem"));
        c.setUsername(rs.getString("username"));
        c.setQuantity(rs.getInt("quantity"));


        return c;
    }
}