package br.andrevasques.spring_exercises.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank String clientId,
        @NotEmpty List<@Valid CreateOrderItemRequest> items
) {
}
