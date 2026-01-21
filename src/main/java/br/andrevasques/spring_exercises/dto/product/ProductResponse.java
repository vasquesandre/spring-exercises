package br.andrevasques.spring_exercises.dto.product;

import br.andrevasques.spring_exercises.model.entitites.Product;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal finalPrice
) {
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscount(),
                product.getFinalPrice()
        );
    }
}
