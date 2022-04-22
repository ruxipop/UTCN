package com.bianca.mappers;

import com.bianca.model.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowMapper implements RowMapper<Wish> {


    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {

        Wish w = new Wish();
        w.setWishId(rs.getInt("wishId"));
        w.setWishItem(rs.getInt("wishItem"));
        w.setUsername(rs.getString("username"));


        return w;
    }
}