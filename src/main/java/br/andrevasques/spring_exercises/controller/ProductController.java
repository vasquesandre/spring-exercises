package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Product newProduct(@Valid Product product) {
        productRepository.save(product);
        return product;
    }

    @GetMapping
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductsById(@PathVariable int id) {
        return productRepository.findById(id);
    }
}