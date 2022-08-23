package co.epam.logchangeslistener.jms;

import com.amazonaws.services.simpleemailv2.AmazonSimpleEmailServiceV2;
import com.amazonaws.services.simpleemailv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImp implements MailService {

    @Value("${email.sender}")
    private String SENDER_EMAIL;

    @Autowired
    private AmazonSimpleEmailServiceV2 amazonSimpleEmailService;

    @Override
    public void sendEmail(String destination, String subject, String body) {
        Destination emailDestination = new Destination().withToAddresses(destination);

        Message message = new Message()
                .withSubject(new Content().withData(subject))
                .withBody(new Body().withText(new Content().withData(body)));

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(emailDestination)
                .withContent(new EmailContent().withSimple(message))
                .withFromEmailAddress(SENDER_EMAIL);

        amazonSimpleEmailService.sendEmail(emailRequest);
    }
}
