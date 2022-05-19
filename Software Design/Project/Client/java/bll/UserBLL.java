package bll;


import java.util.*;
import java.io.*;

import com.*;
import model.*;

@lombok.Getter
public class UserBLL {

    private Client client;
    private List<User> usersList = new ArrayList<model.User>();

    public UserBLL(Client client) {
        this.client = client;

    }


    public List<User> getUsersList() throws java.io.IOException {
        this.client.sendMessage("allUser");

        usersList = (List<User>) (Object) client.listenForMessage();

        return usersList;


    }


    public void updateUser(User user) {

        this.client.sendMessage("updateUser " + user.getFirstName() + " " + user.getLastName() + " " + user.getUsername() + " " + user.getPassword() + " " + user.getRole());


    }

    public void insertUser(User user) {
        this.client.sendMessage("insertUser " + user.getFirstName() + " " + user.getLastName() + " " + user.getUsername() + " " + user.getPassword() + " " + user.getRole());


    }


    public void deleteUser(String username) {
        this.client.sendMessage("deleteUser " + username);


    }


    public User existUser(String username, String password) throws IOException {

        client.sendMessage("existUser " + username + " " + password);


        List<User> user = (List<User>) (Object) client.listenForMessage();

        return user.get(0);

    }

    public List<User> filterUsers(String role) {


        List<User> m = usersList.stream().filter(p -> {
            return (p.getRole().equals(role));

        }).collect(java.util.stream.Collectors.toList());

        if (!m.isEmpty()) {

            return m;

        }

        return null;
    }

    public List<String> typeOfRole() throws java.io.IOException {
        List<String> ss = new ArrayList<String>();
        for (User user : getUsersList()) {

            ss.add(user.getRole());
        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        return ss;
    }


}
