package co.epam.jmstask1orderservice.services.jms;

import co.epam.jmstask1orderservice.core.pojos.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderSenderImpl implements OrderSenderService {

    @Autowired
    JmsTemplate jmsTemplate;
    @Value("${queues.order-queue}")
    private String ORDER_QUEUE_NAME;

    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend(ORDER_QUEUE_NAME, order, postProcessor -> {
            postProcessor.setStringProperty("type", order.getItem().getType());
            return postProcessor;
        });
    }

    public void sendOrders(List<Order> orders) {
        orders.forEach(this::sendOrder);
    }

}
