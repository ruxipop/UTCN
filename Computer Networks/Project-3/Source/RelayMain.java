package School;

public class RelayMain {
    public static void main(String[] args) {

        new RelayNode("127.0.0.1", 5001, 5002,"127.0.0.2");
        new RelayNode("127.0.0.2", 5002, 5003,"127.0.0.3");
        new RelayNode("127.0.0.3", 5003, 5004,null);

    }
}
