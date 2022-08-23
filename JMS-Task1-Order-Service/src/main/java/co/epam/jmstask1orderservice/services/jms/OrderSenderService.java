package co.epam.jmstask1orderservice.services.jms;


import co.epam.jmstask1orderservice.core.pojos.Order;

import java.util.List;

public interface OrderSenderService {
    void sendOrder(Order order);

    void sendOrders(List<Order> orders);
}
