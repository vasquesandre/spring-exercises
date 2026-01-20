package br.andrevasques.spring_exercises.dto.order;

import java.util.List;

public record CreateOrderRequest(
        String clientId,
        List<CreateOrderItemRequest> items
) {
}
