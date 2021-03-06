package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserPersistance {
    private String jdbcURL = "jdbc:mysql://192.168.64.2:3306/moviehouse";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private java.util.List<User> usersList = new java.util.ArrayList<User>();

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (userID, firstName, lastName,username,password1,role) VALUES " +
            " (?,?,?,?,?,?);";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE username = ?;";
    private static final String SELECT_USER_BY_ID = "SELECT firstName,lastName,password1,role FROM users WHERE username =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USERS_SQL = "UPDATE users SET firstName=?  ,lastName=?,password1=?,role=? where username = ?";

    public UserPersistance() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public java.util.List<User> getUsersList() {
        usersList.clear();
        selectAllUsers();
        return usersList;
    }

    public void insertUser(User user) throws SQLException {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(user.getUserID()));
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            printSQLException(e);
        }
    }

    public void deleteUser(String username) throws SQLException {

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setString(1, username);
            statement.executeUpdate();


        } catch (SQLException e) {

            printSQLException(e);
        }
    }

    public void selectAllUsers() {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {

            java.sql.ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("userID");

                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String username = rs.getString("username");
                String password1 = rs.getString("password1");
                String role = rs.getString("role");
                usersList.add(new User(firstName, lastName, username, password1, role));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public void updateUser(User user) throws SQLException {

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            statement.setString(5, user.getUsername());
            statement.executeUpdate();
        }

    }


    public User selectUser(String username) {

        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setString(1, username);
            java.sql.ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password1");
                String role = rs.getString("role");
                user = new User(firstName, lastName, username, password, role);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


}
