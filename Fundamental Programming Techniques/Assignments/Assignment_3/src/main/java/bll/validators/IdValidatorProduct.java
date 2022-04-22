package bll.validators;


import model.Product;

import javax.swing.*;

/**
 * Clasa care implementeaza interfata Validator.
 * Are ca scop validarea id-ului produsului, astfel incat sa nu fie mai mic decat 0.
 */
public class IdValidatorProduct implements Validator<Product> {


    private static final int MIN_ID = 0;


    public void validate(Product p) {

        if (p.getIdProduct() < MIN_ID) {
            JOptionPane.showMessageDialog(null, "Id-ul produsului trebuie sa fie mai mare ca 0!!!", "", JOptionPane.INFORMATION_MESSAGE);

        }

    }

}

