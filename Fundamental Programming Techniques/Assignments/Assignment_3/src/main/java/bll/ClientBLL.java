package bll;

import bll.validators.IdValidatorClient;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa care implementeaza layer-ul business logic pentru tabelul Client.
 */
public class ClientBLL {
    private ClientDAO clientDao;
    private List<Client> clients;
    private List<Validator<Client>> validators;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new IdValidatorClient());
        clientDao = new ClientDAO();
        clients = clientDao.findByAll();
    }

    /**
     * Returneaza clienti care se afla la momentul actual in baza de date.
     */
    public List<Client> getClients() {
        clients = clientDao.findByAll();
        return clients;
    }

    /**
     * Creaza tabelul corespunzator pentru modelul Client.
     *
     * @throws IllegalAccessException
     */
    public TableModel clientTable(List<Client> c) throws IllegalAccessException {
        TableModel df = clientDao.createTableModel(c);
        return df;
    }

    /**
     * Face update la un client , a carui atribute sunt date ca argumente.
     */
    public void updateClient(int id, String name, String address, int age) {
        String[] s = new String[4];
        Client client = clientDao.findByField(id, "idClient");
        s[0] = id + "";
        s[1] = name;
        s[2] = address;
        s[3] = age + "";
        clientDao.update(client.getIdClient(), s, "idClient", client);
        clients = clientDao.findByAll();

    }

    /**
     * Insereaza un client in tabelul corespunzator.
     *
     * @param client clientul care trebuie sa fie introdus in baza de date.
     */

    public void insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        if (client.getIdClient() < 0) {
            return;
        }
        clientDao.insert(client);
        clients = clientDao.findByAll();
    }

    /**
     * Cauta in tabel, un client.
     *
     * @param id id-ul clientului cautat.
     */
    public Client findClient(int id) throws NoSuchElementException {
        Client client = clientDao.findByField(id, "idClient");
        if (client == null) {
            throw new NoSuchElementException("Nu exista clientul cu id " + id);
        }
        return client;
    }

    /**
     * Sterge un client din tabelul corespunzator.
     *
     * @param client clientul care trebuie sa fie sters din baza de date.
     */

    public void deleteClient(Client client) {
        clientDao.delete(client.getIdClient(), "idClient");
        clients = clientDao.findByAll();
    }


}
