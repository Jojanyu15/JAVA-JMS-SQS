package co.epam.logchangeslistener.jms;


import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class ProcessedOrderLogReceiverImp implements ProcessedOrderLogReceiverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessedOrderLogReceiverImp.class);
    @Autowired
    MailService mailService;
    @Value("${email.admin}")
    private String ADMIN_EMAIL;
    private static final String EMAIL_SUBJECT = "New changes inside logfile on s3";

    @JmsListener(destination = "${queues.processed-order}")
    public void receiveOrderStatus(SQSTextMessage processedMessage) throws JMSException {
        LOGGER.info(processedMessage.getText());
        mailService.sendEmail(ADMIN_EMAIL,EMAIL_SUBJECT,processedMessage.getText());
    }
}
