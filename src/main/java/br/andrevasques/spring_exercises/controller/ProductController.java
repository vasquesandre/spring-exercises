package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Product newProduct(@RequestParam String name, @RequestParam double price, @RequestParam double discount) {
        Product product = new Product(name, price, discount);
        productRepository.save(product);
        return product;
    }
}