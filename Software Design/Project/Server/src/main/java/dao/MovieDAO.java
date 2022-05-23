package dao;

import model.*;
import connection.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class MovieDAO extends AbstractDAO<Movie> {
    private static final String COUNT_NB_TYPE = "SELECT COUNT(*) AS  total from Movie WHERE type=?";
    private static final String COUNT_NB_CATEGORY = "SELECT COUNT(*) AS  total from Movie WHERE category=?";

    public int select_type(String type) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int total = 0;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_NB_TYPE);
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }

        } catch (java.sql.SQLException | ClassNotFoundException e) {
            LOGGER.log(java.util.logging.Level.WARNING, "DAO: select category" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return total;
    }

    public int select_category(String category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int total = 0;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_NB_CATEGORY);
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }

        } catch (java.sql.SQLException | ClassNotFoundException e) {
            LOGGER.log(java.util.logging.Level.WARNING, "DAO: select category" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return total;
    }


}
