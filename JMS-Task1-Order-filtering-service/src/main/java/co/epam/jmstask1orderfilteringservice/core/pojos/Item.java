package co.epam.jmstask1orderfilteringservice.core.pojos;

import co.epam.jmstask1orderfilteringservice.core.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private final String name;
    private final double price;
    private final double volume;
    private final ItemType type;
    private final int quantity;

    @JsonCreator
    public Item(@JsonProperty("name") String name,
                @JsonProperty("price") double price,
                @JsonProperty("volume") double volume,
                @JsonProperty("type") ItemType type,
                @JsonProperty("quantity") int quantity) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.type = type;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", type='" + type + '\'' +
                '}';
    }
}
