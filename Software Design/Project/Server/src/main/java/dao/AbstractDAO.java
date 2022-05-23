package dao;

import connection.*;

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


public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) (pt).getActualTypeArguments()[0];
    }


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

        } catch (java.sql.SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findByTwoField(Object field, String fieldId, Object field1, String fieldId1) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectQueryTwo(fieldId, fieldId1);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            preparedStatement.setObject(2, field1);
            resultSet = preparedStatement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (objects.isEmpty()) return null;
            return objects.get(0);

        } catch (java.sql.SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


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

        } catch (java.sql.SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findByField" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


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

        } catch (java.sql.SQLException | IllegalAccessException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }


    public void delete(Object field1, String fieldN, Object field2, String fieldN1) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createDeleteQuery(fieldN, fieldN1);
        try {
            connection = ConnectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field1);
            if (field2 != null) {
                preparedStatement.setObject(2, field2);
            }
            preparedStatement.executeUpdate();

        } catch (java.sql.SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Face update la elementul dat
     *
     * @param update un string care contine valorile campurilor care urmeaza sa fie modificate.
     * @param fieldN este denumirea coloanei  tabelului in care urmeaza sa fie cautat (din baza de date).
     */
    public void update(String[] update, String fieldN, String fieldN1, String correct, String correct1) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = createUpdateQuery(type.getDeclaredFields(), update, fieldN, fieldN1, correct, correct1);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (java.sql.SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }


    private String createSelectQuery(String fieldName) {
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + fieldName + "=?";
        return query;
    }

    private String createSelectQueryTwo(String field1, String field2) {
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE " + field1 + "=?" + " and " + field2 + "=?";
        return query;
    }

    private String createSelectAll() {
        String query = "SELECT * FROM " + type.getSimpleName();
        return query;
    }


    private String createDeleteQuery(String field1, String field2) {
        if (field2 == null) {
            return "DELETE FROM " + type.getSimpleName() + " WHERE " + field1 + "=?";
        }
        return "DELETE FROM " + type.getSimpleName() + " WHERE " + field1 + "=?" + " and " + field2 + "=?";
    }


    private String createInsertQuery(Field[] fields) {

        StringBuilder sb = new StringBuilder().append("INSERT INTO ").append(type.getSimpleName()).append("(");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName()).append(",");
        }
        sb.append(fields[fields.length - 1].getName()).append(") VALUES (").append("?,".repeat(fields.length - 1));
        sb.append("?)");
        return sb.toString();
    }

    private String createUpdateQuery(Field[] fields, String[] update, String fieldId, String fieldId1, String correct, String correct1) {

        StringBuilder sb = new StringBuilder().append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        for (int i = 0; i < fields.length - 1; i++) {

            sb.append(fields[i].getName() + " = '" + update[i] + "', ");
        }
        sb.append(fields[fields.length - 1].getName() + " = '" + update[update.length - 1] + "'");
        if (correct1 == null) {
            sb.append("WHERE " + fieldId + "=" + "'" + correct + "'");
        } else {
            sb.append("WHERE " + fieldId + "=" + "'" + correct + "'" + " and " + fieldId1 + "=" + "'" + correct1 + "'");
        }

        return sb.toString();
    }


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


}