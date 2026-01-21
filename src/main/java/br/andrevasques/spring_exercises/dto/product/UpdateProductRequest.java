package br.andrevasques.spring_exercises.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductRequest (
        String name,
        @Positive BigDecimal price,
        @DecimalMin("0.0") @DecimalMax("1.0") BigDecimal discount
)
{ }
