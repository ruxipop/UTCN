package School;

import java.net.*;
import java.io.*;

@lombok.Getter

public class Client {
    private Socket socket;
    private OutputStream outputStream;
    private String address;
    private int portNb;
    private String clientAddress;  //pentru vizulizare in wireshark

    public Client(String clientAddress, String address, int portNb) {
        this.address = address;  //server to connect adress and port nb
        this.portNb = portNb;
        this.clientAddress = clientAddress;
    }

    public void connect() throws Exception {

        if (this.address != null) {
            socket = new Socket();  //se conecteaza la portul severul de la adresa address si care
            //asculta pe portul portNb
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(this.clientAddress, 9999)); //foo port
            InetSocketAddress serverAddress = new InetSocketAddress(this.address, this.portNb);
            socket.connect(serverAddress);
            if (socket.isConnected()) {
                if (clientAddress.equals("127.0.0.15")) {
                    System.out.println("Sender with address " + this.clientAddress + " was connected");
                }
                outputStream = new DataOutputStream(socket.getOutputStream());
            }
        }
    }

    public void sendMessage(String[] payload) throws Exception {

        String send = payload[0] + "/" + payload[1];
        this.outputStream.write(send.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public void close() throws Exception {
        if (outputStream != null) {
            outputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}
