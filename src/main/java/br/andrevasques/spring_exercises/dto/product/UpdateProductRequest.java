package br.andrevasques.spring_exercises.dto.product;

import java.math.BigDecimal;

public record UpdateProductRequest (
        String name,
        BigDecimal price,
        BigDecimal discount
)
{ }
