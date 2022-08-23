package co.epam.jmstask1orderfilteringservice.services.jms;

import co.epam.jmstask1orderfilteringservice.core.enums.ItemType;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import org.springframework.messaging.handler.annotation.Header;

import javax.jms.JMSException;
import java.io.IOException;

public interface OrderReceiverService {
    void receiveAndProcessOrder(SQSTextMessage sqsOrder, @Header(name = "type") ItemType type) throws JMSException, IOException;
}
