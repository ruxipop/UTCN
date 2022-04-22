package bll.validators;

import model.OrderW;

import javax.swing.*;

/**
 * Clasa care implementeaza interfata Validator.
 * Are ca scop validarea id-ului comenzi, astfel incat sa nu fie mai mic decat 0.
 */

public class IdValidatorOrder implements Validator<OrderW> {

    private static final int MIN_ID = 0;


    public void validate(OrderW o) {

        if (o.getIdOrder() < MIN_ID) {
            JOptionPane.showMessageDialog(null, "Id-ul comenzi trebuie sa fie mai mare ca 0!!!", "", JOptionPane.INFORMATION_MESSAGE);

        }

    }
}

