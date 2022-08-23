package co.epam.jmstask1orderfilteringservice.core.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private Customer customer;
    private Item item;
    private double total;


    @JsonCreator
    public Order(@JsonProperty("customer") Customer customer,
                 @JsonProperty("item") Item item,
                 @JsonProperty("total") double total) {
        this.total = total;
        this.customer = customer;
        this.item = item;
    }

    public double getTotal() {
        return total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", items=" + item +
                ", total=" + total +
                '}';
    }
}
