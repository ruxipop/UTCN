package bll.validators;

import model.Client;

import javax.swing.*;

/**
 * Clasa care implementeaza interfata Validator.
 * Are ca scop validarea id-ului clientul, astfel incat sa nu fie mai mic decat 0.
 */
public class IdValidatorClient implements Validator<Client> {

    private static final int MIN_ID = 0;


    public void validate(Client c) {

        if (c.getIdClient() < MIN_ID) {
            JOptionPane.showMessageDialog(null, "Id-ul clientului trebuie sa fie mai mare ca 0!!!", "", JOptionPane.INFORMATION_MESSAGE);

        }

    }

}


