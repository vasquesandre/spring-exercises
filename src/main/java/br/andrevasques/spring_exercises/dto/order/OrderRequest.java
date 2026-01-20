package br.andrevasques.spring_exercises.dto.order;

import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.valueObjects.OrderItem;

import java.util.List;

public record OrderRequest(
        String orderId,
        Client client,
        List<OrderItem> items
) {
}
