package br.andrevasques.spring_exercises.dto.order;

import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.model.valueObjects.OrderItem;

import java.util.List;

public record OrderResponse(
        String orderId,
        Client client,
        List<OrderItem> items
) {
    public OrderResponse(Order order, Client client) {
        this(
                order.getId(),
                client,
                order.getItems()
        );
    }
}
