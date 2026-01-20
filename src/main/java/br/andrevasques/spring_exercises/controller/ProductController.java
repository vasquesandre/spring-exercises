package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.product.CreateProductRequest;
import br.andrevasques.spring_exercises.dto.product.ProductRequest;
import br.andrevasques.spring_exercises.dto.product.UpdateProductRequest;
import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ProductRequest save(@RequestBody @Valid CreateProductRequest dto) {
        Product product = Product.create(
                dto.name(),
                dto.price(),
                dto.discount()
        );
        Product saved =  productRepository.save(product);

        return new ProductRequest(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getDiscount(),
                saved.getFinalPrice()
        );
    }

    @GetMapping
    public Page<ProductRequest> getProducts() {
        Pageable pageable = PageRequest.of(0, 10);

        return productRepository.findAll(pageable)
                .map(product -> new ProductRequest(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getFinalPrice()
                ));
    }

    @GetMapping("/{id}")
    public ProductRequest getProductById(@PathVariable String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        return new ProductRequest(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscount(),
                product.getFinalPrice()
        );
    }

    @GetMapping("/name")
    public Page<ProductRequest> getProductsByName(@RequestParam String name) {
        Pageable pageable = PageRequest.of(0, 10);
        return productRepository.findByNameContaining(name, pageable)
                .map(product -> new ProductRequest(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getFinalPrice()
                ));
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Page<ProductRequest> getProductsPageable(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        if(pageSize >= 20)  pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable)
                .map(product -> new ProductRequest(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getFinalPrice()
                ));
    }

    @PatchMapping("/{id}")
    public ProductRequest update(@PathVariable String id, @RequestBody UpdateProductRequest dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        product.update(dto.name(), dto.price(), dto.discount());
        Product saved = productRepository.save(product);

        return new ProductRequest(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getDiscount(),
                saved.getFinalPrice()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProductsById(@PathVariable String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
        productRepository.deleteById(product.getId());
    }
}