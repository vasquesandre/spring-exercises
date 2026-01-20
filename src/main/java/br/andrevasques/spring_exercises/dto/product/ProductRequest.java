package br.andrevasques.spring_exercises.dto.product;

import java.math.BigDecimal;

public record ProductRequest (
        String id,
        String name,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal finalPrice
)
{ }
