package br.andrevasques.spring_exercises.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank String name,
        @NotNull @Positive BigDecimal price,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") BigDecimal discount
) { }
