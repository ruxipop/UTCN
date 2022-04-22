package bll;

import bll.validators.IdValidatorProduct;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa care implementeaza layer-ul business logic pentru tabelul Product.
 */
public class ProductBLL {
    private ProductDAO productDao;
    private List<Product> products;
    private List<Validator<Product>> validators;

    public ProductBLL() {
        productDao = new ProductDAO();
        products = productDao.findByAll();
        validators = new ArrayList<Validator<Product>>();
        validators.add(new IdValidatorProduct());
    }

    /**
     * Returneaza produsele care se afla la momentul actual in baza de date.
     */
    public List<Product> getProducts() {
        products = productDao.findByAll();
        return products;
    }

    /**
     * Creaza tabelul corespunzator pentru modelul Product.
     *
     * @throws IllegalAccessException
     */
    public TableModel productTable(List<Product> c) throws IllegalAccessException {
        TableModel df = productDao.createTableModel(c);
        return df;
    }

    /**
     * Cauta in tabel, un produs.
     *
     * @param id id-ul produsului cautat.
     */
    public Product findProduct(int id) throws NoSuchElementException {
        Product product = productDao.findByField(id, "idProduct");
        if (product == null) {
            throw new NoSuchElementException("Nu exista produsul cu id-ul : " + id);
        }
        return product;
    }

    /**
     * Insereaza un produs in tabelul corespunzator.
     *
     * @param product produsul care trebuie sa fie introdus in baza de date.
     */

    public void insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        if (product.getIdProduct() < 0) {
            return;
        }

        productDao.insert(product);
        products = productDao.findByAll();
    }

    /**
     * Face update la cantitatea unui produs din tabelul Produs,
     * atunci cand se adauga la comanda o anumita cantitate din acest produs.
     */

    public void updateQuantity(int id, int quantity) {
        (productDao).updateQuantity(id, quantity);
    }

    /**
     * Face update la un produs , a carui atribute sunt date ca argumente.
     */
    public void updateProduct(int id, int quantity, String name, double price) {

        String[] s = new String[4];
        Product product = productDao.findByField(id, "idProduct");

        s[0] = id + "";
        s[2] = quantity + "";
        s[1] = name;
        s[3] = price + "";


        productDao.update(product.getIdProduct(), s, "idProduct", product);
        products = productDao.findByAll();

    }


    /**
     * Sterge un produs din tabelul corespunzator.
     *
     * @param product produsul care trebuie sa fie sters din baza de date.
     */

    public void deleteProduct(Product product) {


        productDao.delete(product.getIdProduct(), "idProduct");


        products = productDao.findByAll();
    }


}
