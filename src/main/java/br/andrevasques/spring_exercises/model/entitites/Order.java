package br.andrevasques.spring_exercises.model.entitites;

import br.andrevasques.spring_exercises.model.valueObjects.OrderItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String clientId;
    private List<OrderItem> items;

    public Order(String clientId) {
        this.clientId = clientId;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        if(item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        items.add(item);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return clientId;
    }

    public void setClient(String clientId) {
        this.clientId = clientId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
