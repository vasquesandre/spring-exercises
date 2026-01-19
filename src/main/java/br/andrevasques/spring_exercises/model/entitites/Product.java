package br.andrevasques.spring_exercises.model.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    private BigDecimal price;
    private BigDecimal discount;

    public Product() {
    }

    public Product(String name, BigDecimal price, BigDecimal discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public static Product create(String name, BigDecimal price, BigDecimal discount) {
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if(discount == null || discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException(("Discount must be between 0.0 and 1.0"));
        }

        return new Product(name, price, discount);
    }

    public Product update(String name, BigDecimal price, BigDecimal discount) {
        if(name != null && !name.isBlank()) {
            this.name = name;
        }

        if(price != null) {
            if(price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero");
            }
            this.price = price;
        }

        if(discount != null) {
            if(discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.ONE) > 0) {
                throw new IllegalArgumentException(("Discount must be between 0.0 and 1.0"));
            }
            this.discount = discount;
        }

        return this;
    }

    public BigDecimal getFinalPrice() {
        return this.price.multiply(BigDecimal.ONE.subtract(this.discount));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
