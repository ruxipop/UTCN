package com;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ObjectInputStream fromServer;

    public Client() {

    }

    public void connect() throws java.io.IOException {
        socket = new Socket("127.0.0.1", 5001);
        this.bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.fromServer = new java.io.ObjectInputStream(socket.getInputStream());

    }

    public void sendMessage(String message) {
        try {


            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int listenForInt() throws IOException {


        int message = 0;
        try {

            message = bufferedReader.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public List<Object> listenForMessage() throws java.io.IOException {


        List<Object> message = null;
        try {

            message = (ArrayList<Object>) fromServer.readObject();

        } catch (java.io.IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }


    public void closeConnection() {
        try {
            socket.close();

        } catch (Exception e1) {
            System.err.println("error");
        }
    }

}
