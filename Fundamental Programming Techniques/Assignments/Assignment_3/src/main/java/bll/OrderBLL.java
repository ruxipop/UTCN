package bll;


import bll.validators.IdValidatorOrder;
import bll.validators.Validator;
import dao.OrderDAO;
import model.OrderW;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care implementeaza layer-ul business logic pentru tabelul Order.
 */
public class OrderBLL {
    private OrderDAO orderDao;
    private List<OrderW> orders;
    private List<Validator<OrderW>> validators;

    public OrderBLL() {
        validators = new ArrayList<Validator<OrderW>>();
        validators.add(new IdValidatorOrder());
        orderDao = new OrderDAO();
        orders = orderDao.findByAll();
    }

    /**
     * Returneaza comenziile care se afla la momentul actual in baza de date.
     */
    public List<OrderW> getOrders() {
        orders = orderDao.findByAll();
        return orders;
    }

    /**
     * Creaza tabelul corespunzator pentru modelul OrderW.
     *
     * @throws IllegalAccessException
     */
    public TableModel orderTable(List<OrderW> c) throws IllegalAccessException {
        TableModel df = orderDao.createTableModel(c);
        return df;
    }

    /**
     * Insereaza o comanda  in tabelul corespunzator.
     *
     * @param order comanda care trebuie sa fie introdusa in baza de date.
     */
    public void insertOrder(OrderW order) {
        for (Validator<OrderW> v : validators) {
            v.validate(order);
        }
        if (order.getIdOrder() < 0) {
            return;
        }

        orderDao.insert(order);
        orders = orderDao.findByAll();
    }


}
