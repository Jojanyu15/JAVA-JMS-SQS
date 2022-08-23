package co.epam.jmstask1orderlogservice.services.jms;


import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.Date;

@Component
public class OrderStatusReceiverImp implements OrderStatusReceiverService {
    @Autowired
    OrderPublisherService orderPublisherService;

    @JmsListener(destination = "${queues.processed-order}")
    public void receiveOrderStatus(SQSTextMessage processedMessage) throws JMSException, IOException {
        Date date = new Date();
        date.setTime(processedMessage.getJMSTimestamp());
        orderPublisherService.publishToS3(processedMessage.getText(), date.toString());
    }
}
