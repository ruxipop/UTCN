package Model;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;

    public UserBuilder() {

    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder role(String role) {
        this.role = role;
        return this;
    }

    public User build() {
        return new Model.User(firstName, lastName, username, password, role);
    }

}
