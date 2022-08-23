package co.epam.jmstask1orderfilteringservice.services.jms;

import co.epam.jmstask1orderfilteringservice.core.enums.ItemType;
import co.epam.jmstask1orderfilteringservice.core.pojos.Order;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.io.IOException;

@Component
public class OrderReceiverImp implements OrderReceiverService {

    @Autowired
    OrderProcessorService orderProcessorService;

    @JmsListener(destination = "${queues.order-queue}")
    public void receiveAndProcessOrder(SQSTextMessage sqsOrder, @Header(name = "type") ItemType type) throws JMSException, IOException {
        Order order = new ObjectMapper().readValue(sqsOrder.getText(), Order.class);
        orderProcessorService.processOrder(order, type);
    }
}
