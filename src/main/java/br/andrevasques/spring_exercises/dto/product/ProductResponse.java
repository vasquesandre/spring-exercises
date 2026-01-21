package br.andrevasques.spring_exercises.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal finalPrice
)
{ }
