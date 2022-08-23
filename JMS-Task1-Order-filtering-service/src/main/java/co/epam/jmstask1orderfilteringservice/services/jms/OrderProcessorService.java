package co.epam.jmstask1orderfilteringservice.services.jms;

import co.epam.jmstask1orderfilteringservice.core.enums.ItemType;
import co.epam.jmstask1orderfilteringservice.core.pojos.Order;

public interface OrderProcessorService {
    void processOrder(Order order, ItemType type);
}
