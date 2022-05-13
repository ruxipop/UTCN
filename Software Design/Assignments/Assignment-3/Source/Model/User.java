package Model;

public class User extends Person {
    private static int userID = 0;

    private String username;
    private String password;
    private String role;

    public User(String firstName, String lastName, String username, String password, String role) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
        this.role = role;
        userID++;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
