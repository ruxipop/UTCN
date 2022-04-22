package com.bianca.dao;

import com.bianca.mappers.FlowerRowMapper;
import com.bianca.model.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
public class FlowerDAOImpl implements FlowerDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Flower> loadFlowers() {

        String sql = "SELECT * FROM flori";

        return jdbcTemplate.query(sql, new FlowerRowMapper());
    }

    @Override
    public void insertFlower(Flower flower) {
        String pattern = "yyyy-MM-dd";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        flower.setData(dateInString);
        String category = flower.getCategorie();
        System.out.println(flower.getePromotie());
        switch (category){

            case "1" :
                flower.setCategorie("Craciun");
                break;
            case "2" :
                flower.setCategorie("Aranjamente Nunta");
                break;
            case "3" :
                flower.setCategorie("Buchete");
                break;
            case "4" :
                flower.setCategorie("Criogenati");
                break;
            case "5" :
                flower.setCategorie("Cutii flori");
                break;
            case "6" :
                flower.setCategorie("Funerar");
                break;
            default :
                flower.setCategorie("Craciun");
        }

        Object[] sqlArgs = {flower.getNume(), flower.getPret(), flower.getDescriere(), flower.getCategorie(), flower.getData(),  flower.getImg(), flower.getePromotie(), flower.getPretNou()};
        String sql = "INSERT INTO flori(nume, pret, descriere, categorie, data, img, ePromotie, pretNou) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, sqlArgs);
        System.out.println("1 flower inserted");
    }

    @Override
    public Flower getFlowerById(int id) {
        String sql = "SELECT * FROM flori WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new FlowerRowMapper(), id);
    }


    @Override
    public List<Flower> getFlowerByNume(String nume) {
        String sql = "SELECT * FROM flori WHERE LOWER(nume) LIKE ?";
        return jdbcTemplate.query(sql, new FlowerRowMapper(), "%"+ nume + "%");
    }

    @Override
    public void updateFlower(Flower flower) {
        String sql = "UPDATE flori SET nume = ?, pret = ?, categorie = ?, descriere = ?, data = ?, img = ?, ePromotie = ?, pretNou = ? WHERE id = ? ";
        String category = flower.getCategorie();
        switch (category){

            case "1" :
                flower.setCategorie("Craciun");
                break;
            case "2" :
                flower.setCategorie("Aranjamente Nunta");
                break;
            case "3" :
                flower.setCategorie("Buchete");
                break;
            case "4" :
                flower.setCategorie("Criogenati");
                break;
            case "5" :
                flower.setCategorie("Cutii flori");
                break;
            case "6" :
                flower.setCategorie("Funerar");
                break;
            default :
                flower.setCategorie("Craciun");
        }
        Object[] sqlArgs = {flower.getNume(), flower.getPret(), flower.getCategorie(), flower.getDescriere(), flower.getData(), flower.getImg(), flower.getePromotie(), flower.getPretNou(), flower.getId()};
        jdbcTemplate.update(sql, sqlArgs);
    }

    @Override
    public void deleteFlower(int id) {
        String sql = "DELETE FROM flori WHERE id = " + id;
        jdbcTemplate.update(sql);
    }


}
