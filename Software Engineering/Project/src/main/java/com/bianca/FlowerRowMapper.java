package com.bianca;

import com.bianca.model.Flower;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlowerRowMapper implements RowMapper<Flower> {
    @Override
    public Flower mapRow(ResultSet rs, int rowNum) throws SQLException {

        Flower f = new Flower();
        f.setId(rs.getInt("id"));
        f.setNume(rs.getString("nume"));
        f.setPret(rs.getDouble("pret"));
        f.setDescriere(rs.getString("descriere"));
        f.setCategorie(rs.getString("categorie"));
        f.setData(String.valueOf(rs.getDate("data")));
        f.setImg(rs.getString("img"));
        int promo = rs.getInt("ePromotie");
        if(promo == 0) f.setePromotie(false);
        else f.setePromotie(true);
        f.setPretNou(rs.getDouble("pretNou"));



        return f;
    }
}
