package dao;

import connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Classes
 * Clasa generica  care contine metodele pentru modelarea  bazei de date.
 * Metodele sunt  implementate prin tehnica reflection.
 *
 * @param <T> este tipul clasei Model -Client/Product/OrderV.
 */

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) (pt).getActualTypeArguments()[0];
    }

    /**
     * Cauta si returneaza obiectul care indeplineste conditia  fieldName=field din baza de date.
     *
     * @param field     valorea care se cauta in tabel.
     * @param fieldId este denumirea coloanei  tabelului in care urmeaza sa fie cautat (din baza de date).
     *                  Returneaza primul obiect gasit de tip T din baza de date ,sau null daca acesta nu exista.
     */
    public T findByField(Object field, String fieldId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(fieldId);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            resultSet = preparedStatement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (objects.isEmpty()) return null;
            return objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Preia toate obiectele de tip T gasite in baza de date.
     *
     * @return o lista cu toate obiectele care se gasesc la momentul actual in baza de date,sau null daca nu exista.
     */
    public List<T> findByAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectAll();
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<T> objects = createObjects(resultSet);
            if (objects.isEmpty()) return null;
            return objects;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * Insereaza elementul dat in tabelul corespunzator din baza de date.
     *
     * @param item este elementul care trebuie sa fie inserat.
     */


    public void insert(T item) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = createInsertQuery(type.getDeclaredFields());
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            int index = 1;

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                preparedStatement.setObject(index, field.get(item));
                index++;
            }
            preparedStatement.executeUpdate();

        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Sterge randurile din tabelul corespunzator ,care indeplinesc conditia ca fieldId=field.
     *
     * @param field   valorea care se cauta in tabel.
     * @param fieldN este denumirea coloanei  tabelului in care urmeaza sa fie cautat (din baza de date).
     */

    public void delete(Object field, String fieldN) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createDeleteQuery(fieldN);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Face update la elementul dat
     *
     * @param field   valorea care se cauta in tabel.
     * @param update  un string care contine valorile campurilor care urmeaza sa fie modificate.
     * @param fieldN este denumirea coloanei  tabelului in care urmeaza sa fie cautat (din baza de date).
     */
    public void update(Object field, String[] update, String fieldN, T item) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = createUpdateQuery(type.getDeclaredFields(), update, fieldN);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }


    /**
     * Formeaza o interogare de tip Selectie, conditionata de valoarea unei anumite coloane.
     *
     * @param fieldName este coloana pe care se face selectia.
     * @return interogare de selectie a  randurilor care indeplinesc conditia din tabelul corespunzator.
     */
    private String createSelectQuery(String fieldName) {
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + fieldName + "=?";
        return query;
    }

    /**
     * Formeaza o interogare de tip Select all,pentru un obiect de tip T.
     *
     * @return interogare de selectie a tuturor randurilor din tabelul corespunzator.
     */
    private String createSelectAll() {
        String query = "SELECT * FROM " + type.getSimpleName();
        return query;
    }

    /**
     * Formeaza o interogare de tip Delete, conditionata de valoarea unei anumite coloane.
     *
     * @param fieldName este coloana care va influenta stergerea.
     * @return interogare de stergere a  randurilor care indeplinesc conditia din tabelul corespunzator.
     */
    private String createDeleteQuery(String fieldName) {
        return "DELETE FROM " + type.getSimpleName() + " WHERE " + fieldName + "=?";
    }
    /**
     * Formeaza o interogare  de tip inserare pentru un obiect de tip T.
     *
     * @param fields contine toate coloanele din tabelul corespunzator obiectului de tip T.
     * @return interogarea de inserare.
     */
    private String createInsertQuery(Field[] fields) {

        StringBuilder sb = new StringBuilder().append("INSERT INTO ").append(type.getSimpleName()).append("(");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(",");
        }
        sb.append(fields[fields.length - 1].getName()).append(") VALUES (").append("?,".repeat(fields.length - 1));
        sb.append("?)");
        return sb.toString();
    }

    /**
     * Formeaza o interogare  de tip update  pentru un obiect de tip T.
     *
     * @param fields  contine toate coloanele din tabelul corespunzator obiectului de tip T.
     * @param update  contine valoriile campurilor care urmeaza sa fie schimbate.
     * @param fieldId este denumirea coloanei  tabelului.
     * @return interogarea de update.
     */
    private String createUpdateQuery(Field[] fields, String[] update, String fieldId) {
        StringBuilder sb = new StringBuilder().append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName() + " = '" + update[i] + "', ");
        }
        sb.append(fields[fields.length - 1].getName() + " = '" + update[update.length - 1] + "'");
        sb.append("WHERE " + fieldId + "=" + update[0]);

        return sb.toString();
    }

    /**
     * Converteste un  {@code ResultSet} intr-o {@code List} de obiecte de tip T.
     *
     * @param resultSet este rezultatul care urmeaza sa fie convertit.
     * @return {@code List} a obiectelor de tip T obtinute din resultSet.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (SQLException | IntrospectionException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Metoda care ajuta la formarea unui tabel de tip T care va fi afisat in interfata grafica.
     *
     * @param lista contine lista de obiecte de tip T care vor fi afisate in tabel.
     *              Daca lista nu contine nici un obiect ,tabelul rezultat va fi gol.
     */

    public TableModel createTableModel(List<T> lista) throws IllegalAccessException {
        Object[][] data;
        Object[] e;
        Field[] fields = type.getDeclaredFields();
        String[] column = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            column[i] = fields[i].getName();
        }
        if (lista == null) {
            data = new Object[2][fields.length];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < fields.length; j++) {
                    data[i][j] = "";
                }
            }
        } else {
            e = lista.toArray();
            data = new Object[e.length][fields.length];
            for (int i = 0; i < e.length; i++) {
                for (int j = 0; j < fields.length; j++) {
                    data[i][j] = fields[j].get(e[i]);
                }
            }
        }
        return new DefaultTableModel(data, column);
    }


}
