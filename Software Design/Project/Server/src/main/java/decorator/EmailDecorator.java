package decorator;

import javax.mail.Message.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailDecorator implements Notifier {

    public void send(String message) {
        // Recipient's email ID needs to be mentioned.
        final String to = "pop_ruxi@yahoo.com";

        // Sender's email ID needs to be mentioned
        final String from = "pop_ruxi@yahoo.com";
        String host = "smtp.mail.yahoo.com";
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pop_ruxi", "jalkpwtkwizmduxw");
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message1 = new javax.mail.internet.MimeMessage(session);

            message1.setFrom(new javax.mail.internet.InternetAddress(from));
            message1.addRecipient(RecipientType.TO, new InternetAddress(to));
            message1.setSubject("Update!");
            message1.setText(message);
            javax.mail.Transport.send(message1);
            System.out.println("Sent message successfully....");
        } catch (javax.mail.MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
