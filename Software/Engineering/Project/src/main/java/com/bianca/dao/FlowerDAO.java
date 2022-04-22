package com.bianca.dao;

import com.bianca.model.Flower;

import java.util.List;

public interface FlowerDAO {

    List<Flower> loadFlowers();
    void insertFlower(Flower flower);
    Flower getFlowerById(int id);
    List<Flower> getFlowerByNume(String nume);
    void updateFlower(Flower flower);
    void deleteFlower(int id);
}
