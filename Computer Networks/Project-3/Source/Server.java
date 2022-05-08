package School;

import java.net.*;
import java.io.*;
@lombok.Getter
public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStream inputStream;
    private int portNb;  //serverul asculta la acest port
    private String ipAddress;  //adresa local pentru server

    public Server(String ipAddress, int portNb) {
        this.portNb = portNb;
        this.ipAddress = ipAddress;
    }

    public void start() throws Exception {
        this.serverSocket = new ServerSocket();
        this.serverSocket.setReuseAddress(true);
        this.serverSocket.bind(new InetSocketAddress(this.ipAddress, this.portNb));
        this.socket = serverSocket.accept();
        this.inputStream = new DataInputStream(this.socket.getInputStream());
    }

    public String[] receiveMessage() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[40];
        byteArrayOutputStream.write(buffer, 0, inputStream.read(buffer));
        String payload = byteArrayOutputStream.toString();
        String split = "/";
        String[] parts = payload.split(split);
        byteArrayOutputStream.flush();

        return parts;

    }

    public void close() throws Exception {
        this.socket.close();
        this.inputStream.close();
    }

}
