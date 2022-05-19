package decorator;

import com.twilio.type.*;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;

public class SMSDecorator implements Notifier {


    @Override
    public void send(String message) {
        Twilio.init("ACb7bd4ce1f84f646d663ba2f4de1fc457", "6b578b47536e198931d6f326932e7921", "ACb7bd4ce1f84f646d663ba2f4de1fc457");

        Message message1 = Message.creator(
                new PhoneNumber("+40758559575"),
                new PhoneNumber("+19207893756"),
                message)
                .create();
        System.out.printf("Send");
    }
}
