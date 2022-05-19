package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private boolean active;

    public Server(int port) {
        this.port = port;
        this.active = true;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(5001);
            while (active) {
                Socket socket = serverSocket.accept();
                System.out.println("New client is connected!\n");
                com.ClientThread clientThread = new com.ClientThread(socket, this);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            active = false;
            serverSocket.close();

        } catch (IOException ex) {
            // Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
