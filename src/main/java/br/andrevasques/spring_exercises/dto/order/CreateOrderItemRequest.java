package br.andrevasques.spring_exercises.dto.order;

public record CreateOrderItemRequest(
        String productId,
        Integer quantity
) {
}
