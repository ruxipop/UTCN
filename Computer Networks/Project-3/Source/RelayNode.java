package School;
public class RelayNode {
    private Server server;
    private Client client;
    private String ipAddress;
    private int portNb;
    private String nextAddress;
    private ClientThread myThread;

    public RelayNode(String ipAddress, int portNb, int portNextHop, String nextAddress) {

        this.client = new Client(ipAddress, nextAddress, portNextHop);
        this.server = new Server(ipAddress, portNb);
        this.ipAddress = ipAddress;
        this.portNb = portNb;
        this.nextAddress = nextAddress;
        myThread = new ClientThread();
        myThread.start();
    }

    private void sendMessageToNext(String[] payload) throws Exception {

        System.out.println("Hop from " + this.ipAddress +
                " to " + this.nextAddress
                +
                " for " + payload[0] + "/" + payload[1]);

        client.sendMessage(payload);

    }

    public void processData() throws Exception {

        while (true) {

            String[] payload = server.receiveMessage();

            if (payload[0].equals(this.ipAddress)) {

                System.out.println("Packet (" + payload[1] + ") for "
                        + this.ipAddress +
                        " from " +
                        server.getSocket().getInetAddress().getHostAddress());
            } else {

                sendMessageToNext(payload);
            }
        }
    }

    public void close() throws Exception {

        if (client != null) {
            this.client.close();
        }
        if (server != null) {
            this.server.close();
        }

        System.out.println("Closed thread");
    }


    private class ClientThread extends Thread {

        @Override
        public void run() {
            try {
                server.start();
                client.connect();
                processData();

            } catch (Exception exception) {
                try {
                    close();
                } catch (Exception exception1) {
                    exception1.printStackTrace();
                }
            }
        }
    }
}
