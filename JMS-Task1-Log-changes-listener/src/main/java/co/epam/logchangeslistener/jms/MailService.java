package co.epam.logchangeslistener.jms;

public interface MailService {
    void sendEmail(String destination, String subject, String body);
}
