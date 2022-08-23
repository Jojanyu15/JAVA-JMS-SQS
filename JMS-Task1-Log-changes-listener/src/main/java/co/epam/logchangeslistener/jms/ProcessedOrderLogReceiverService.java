package co.epam.logchangeslistener.jms;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;

import javax.jms.JMSException;
import java.io.IOException;

public interface ProcessedOrderLogReceiverService {
    void receiveOrderStatus(SQSTextMessage processedMessage) throws JMSException, IOException;
}
