package co.epam.jmstask1orderfilteringservice.services.jms;

import co.epam.jmstask1orderfilteringservice.core.enums.ItemType;
import co.epam.jmstask1orderfilteringservice.core.pojos.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessorImp implements OrderProcessorService {

    @Autowired
    JmsTemplate jmsTemplate;
    @Value("${queues.processed-order}")
    private String PROCESSED_ORDER_QUEUE_NAME;
    private static final double MAX_VOLUME = 30.0;
    private static final double MAX_TOTAL = 60.0;
    private static final String REJECTED_MESSAGE_TEMPLATE = "The order was rejected for the following reasons: ";

    @Override
    public void processOrder(Order order, ItemType type) {
        jmsTemplate.convertAndSend(PROCESSED_ORDER_QUEUE_NAME, getMessage(order, type));
    }

    private String getMessage(Order order, ItemType type) {
        return isOrderInvalid(order) ? getErrorMessage(order, type) : getOrderProcessedMessage(order);
    }

    private String getOrderProcessedMessage(Order order) {
        return String.format("The order with %s for %s was processed successfully",
                order.getItem().getName(),
                order.getCustomer().getFullName());
    }

    private double calculateTotalVolume(Order order) {
        return order.getItem().getVolume() * order.getItem().getQuantity();
    }

    private boolean isOrderInvalid(Order order) {
        return doesExceedMaximumLiquidVolume(order) || doesExceedMaximumTotal(order);
    }

    private boolean doesExceedMaximumLiquidVolume(Order order) {
        return calculateTotalVolume(order) > MAX_VOLUME;
    }

    private boolean doesExceedMaximumTotal(Order order) {
        return order.getTotal() > MAX_TOTAL;
    }

    private String getErrorMessage(Order order, ItemType type) {
        String message = REJECTED_MESSAGE_TEMPLATE;
        if (doesExceedMaximumTotal(order)) {
            message += "Exceeded the maximum total.";
        }
        if (type == ItemType.LIQUID_ITEM && doesExceedMaximumLiquidVolume(order)) {
            message += "Exceeded the maximum liquid volume.";
        }
        return message;
    }


}
