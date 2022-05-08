package School;
import java.util.*;

public class SenderMain {
    public static void main(String[] args) throws Exception {
        //senderul se conecteaza la serverul de la adresa 127.0.0.1 ce asculta pe portul 5001
        Client client = new Client("127.0.0.15", "127.0.0.1", 5001);
        client.connect();

        List<String> ipAddresses = new ArrayList<>(List.of(new String[]
                {"127.0.0.1", "127.0.0.2", "127.0.0.3"
                }));

        for (int i = 0; i < 100; i++) {
            String ip = ipAddresses.get(new Random().nextInt(ipAddresses.size()));
            String[] senderText = new String[]{ip, String.valueOf(i)};
            String str = senderText[0] + "/" + senderText[1];
            System.out.println(str);
            client.sendMessage(senderText);
            Thread.sleep(50);
        }
        client.close();
    }
}
