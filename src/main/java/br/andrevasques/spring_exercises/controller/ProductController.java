package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Product saveProduct(@Valid Product product) {
        productRepository.save(product);
        return product;
    }

    @GetMapping
    public Iterable<Product> getProductsDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        return productRepository.findAll(pageable);
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Iterable<Product> getProductsPageable(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        if(pageSize >= 20)  pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable int id) {
        return productRepository.findById(id);
    }

    @GetMapping("/name")
    public Iterable<Product> getProductsByName(@RequestParam String name) {
        return productRepository.findByNameContaining(name);
    }

    @DeleteMapping("/{id}")
    public void deleteProductsById(@PathVariable int id) {
        productRepository.deleteById(id);
    }
}