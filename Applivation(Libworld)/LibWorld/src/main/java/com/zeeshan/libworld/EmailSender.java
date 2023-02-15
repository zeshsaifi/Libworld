package com.zeeshan.libworld;

import javafx.scene.control.Alert;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    EmailSender(String Email,int OTP){
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.EnableSSL.enable", "true");

        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kumarprianshu8375@gmail.com", "kbfzleetkemjmhka");
            }
        });

            MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("kumarprianshu8375@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
            message.setSubject("Lib World OTP Service.");
            message.setContent("<h2>Your OTP is " + OTP + ".</h2><h3>Thank you for visiting us.</h3>Regards<br>Lib World.<br>Gather more knowledge.....", "text/html");

            System.out.println("e-mail sending..........");
            Transport.send(message);
            System.out.println("e-mail sent..........");

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("OTP sent");
            a.setContentText("OTP has been sent.\nPlease check your spam session.");
            a.setHeaderText(null);
            a.show();
        }
        catch (MessagingException e) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Connection error");
            a.setContentText("Please check your\ninternet connection.\n\nThank-you");
            a.setHeaderText(null);
            a.show();
            System.out.println("ERROR:\n"+e);
        }
    }
}
