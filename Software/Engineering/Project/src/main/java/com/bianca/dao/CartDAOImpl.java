package com.bianca.dao;

import com.bianca.mappers.CartRowMapper;
import com.bianca.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO{
    int number=1;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Cart> loadCarts() {

        String sql = "SELECT * FROM cosuri";

        return jdbcTemplate.query(sql, new  CartRowMapper());
    }

    @Override
    public void insertCart(Cart cart) {
        boolean i=false;

        List<Cart> cartList = loadCarts();

        for(Cart c:cartList){
            if(c.getCartItem()==cart.getCartItem() && c.getUsername().equals(cart.getUsername())){
                number=c.getQuantity()+1;

                Object[] sqlArgs = {cart.getCartId(), number, cart.getCartItem(), cart.getUsername()};
                String sql = "UPDATE cosuri SET cartId = ?, quantity=? where cartItem=? and username = ? ";

                jdbcTemplate.update(sql, sqlArgs);
                i=true;

            }
        }
        if(i!=true){
            number=1;
            Object[] sqlArgs = {cart.getCartItem(), cart.getUsername(),number};
            String sql = "INSERT INTO cosuri(cartItem, username, quantity) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, sqlArgs);
            System.out.println("1 flower inserted");
        }


    }

    @Override
    public Cart getCartByCartItemAndUsername(int id, String username) {
        Object[] sqlArgs = {id, username};
        String sql = "SELECT * FROM cosuri WHERE cartItem = ? and username = ?";

        return jdbcTemplate.queryForObject(sql, new CartRowMapper(), sqlArgs);

    }

    @Override
    public List<Cart> getCartsByUsername(String username) {
        String sql = "SELECT * FROM cosuri WHERE username = '" + username + "'";

        return jdbcTemplate.query(sql, new  CartRowMapper());
    }

    @Override
    public void updateCart(Cart cart) {
        int nb=cart.getQuantity()-1;
        Object[] sqlArgs = {cart.getCartId(),nb,cart.getCartItem(), cart.getUsername()};
        String sql = "UPDATE cosuri SET  cartId = ?, quantity=? WHERE cartItem = ? and username = ? ";
        jdbcTemplate.update(sql, sqlArgs);

    }
    @Override
    public void deleteCart(int id, String username) {
        String sql = "DELETE FROM cosuri WHERE cartItem = " + id + " and username = '" + username + "'";
        jdbcTemplate.update(sql);
    }
}