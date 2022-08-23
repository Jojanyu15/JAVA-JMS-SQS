package co.epam.jmstask1orderlogservice.services.jms;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;

import javax.jms.JMSException;
import java.io.IOException;

public interface OrderStatusReceiverService {
    void receiveOrderStatus(SQSTextMessage processedMessage) throws JMSException, IOException;
}
