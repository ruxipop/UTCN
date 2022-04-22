package com.bianca.model;

public class Flower {

    private int id;
    private String nume;
    private double pret;
    private String descriere;
    private String categorie;
    private String data;
    private String img;
    private boolean ePromotie;
    private Double pretNou;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean getePromotie() {
        return ePromotie;
    }

    public void setePromotie(boolean ePromotie) {
        this.ePromotie = ePromotie;
    }

    public Double getPretNou() {
        return pretNou;
    }

    public void setPretNou(Double pretNou) {
        this.pretNou = pretNou;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                ", categorie='" + categorie + '\'' +
                ", data='" + data + '\'' +
                ", img='" + img + '\'' +
                ", ePromotie=" + ePromotie +
                ", pretNou=" + pretNou +
                '}';
    }
}
