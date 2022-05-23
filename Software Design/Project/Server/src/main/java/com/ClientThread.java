package com;

import java.io.*;
import java.net.*;

import dao.*;

import java.util.*;

public class ClientThread extends Thread {
    private Socket socket;
    private Server server;
    private UserDAO userDAO;
    private MovieDAO movieDAO;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream outputStream;
    private decorator.Notifier notifier;


    public ClientThread(Socket socket, Server server) throws java.io.IOException {

        this.socket = socket;
        this.server = server;
        this.userDAO = new UserDAO();
        this.movieDAO = new dao.MovieDAO();
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        System.out.println("Client thread is executing..");
        try {
            handleRequest();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error");
        }
        System.out.println("Client has disconnected. Socket is closed.\n\n");
       // server.stopServer();
    }

    private void handleRequest() throws IOException, ClassNotFoundException {
        while (!isInterrupted()) {
            System.out.println("Handling clients requests..\n");

            String clientsMessage = bufferedReader.readLine();
            String[] splited = clientsMessage.split(" ");
            List<model.User> users = new java.util.ArrayList<>();
            List<model.Movie> movies = new java.util.ArrayList<>();
            model.User user = null;
            model.Movie movie = null;
            try {
                switch (splited[0]) {
                    case "existUser":
                        user = userDAO.findByTwoField(splited[1], "username", splited[2], "password");
                        users.add(user);
                        outputStream.writeObject(users);
                        outputStream.flush();
                        break;
                    case "allUser":
                        users = userDAO.findByAll();
                        outputStream.writeObject(users);
                        outputStream.flush();
                        users.clear();
                        break;
                    case "insertUser":
                        userDAO.insert(new model.User(splited[1], splited[2], splited[3], splited[4], splited[5]));
                        break;
                    case "deleteUser":
                        userDAO.delete(splited[1], "username", null, null);
                        break;
                    case "updateUser":


                        String[] s = new String[5];
                        s[0] = splited[1];
                        s[1] = splited[2];
                        s[2] = splited[3];
                        s[3] = splited[4];
                        s[4] = splited[5];

                        userDAO.update(s, "username", null, s[2], null);
                        sendNotifier("Utilizatorule " + s[2] + " ti-au fost modificate detaliile");
                        break;

                    case "allMovie":

                        movies = movieDAO.findByAll();
                        outputStream.writeObject(movies);
                        outputStream.flush();
                        movies.clear();
                        break;
                    case "deleteMovie":
                        movieDAO.delete(splited[1], "title", splited[2], "year");

                        break;
                    case "insertMovie":
                        movieDAO.insert(new model.Movie(splited[1], splited[2], splited[3], Integer.valueOf(splited[4])));
                        break;


                    case "updateMovie":
                        String[] s1 = new String[4];
                        s1[0] = splited[1];
                        s1[1] = splited[2];
                        s1[2] = splited[3];
                        s1[3] = splited[4];

                        movieDAO.update(s1, "title", "year", s1[0], splited[5]);
                        break;
                    case "selectType":
                        int i = movieDAO.select_type(splited[1]);
                        bufferedWriter.write(i);
                        bufferedWriter.flush();
                        break;
                    case "selectCategory":

                        int j = movieDAO.select_category(splited[1]);
                        bufferedWriter.write(j);
                        bufferedWriter.flush();
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNotifier(String message) {
        notifier = new decorator.SMSDecorator();
        notifier.send(message);
        notifier = new decorator.EmailDecorator();
        notifier.send(message);
    }
}
