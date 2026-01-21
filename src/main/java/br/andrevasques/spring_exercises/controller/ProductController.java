package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.product.CreateProductRequest;
import br.andrevasques.spring_exercises.dto.product.ProductResponse;
import br.andrevasques.spring_exercises.dto.product.UpdateProductRequest;
import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody @Valid CreateProductRequest dto) {
        Product saved = productService.createProduct(dto);

        return new ProductResponse(saved);
    }

    @GetMapping
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productService.getProducts(pageable)
                .map(ProductResponse::new);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);

        return new ProductResponse(product);
    }

    @GetMapping("/name")
    public Page<ProductResponse> getProductsByName(@RequestParam String name) {
        Pageable pageable = PageRequest.of(0, 10);

        return productService.getProductsByNameContaining(name, pageable)
                .map(ProductResponse::new);
    }

    @PatchMapping("/{id}")
    public ProductResponse update(@PathVariable String id, @RequestBody UpdateProductRequest dto) {
        Product saved = productService.updateProductById(id, dto);

        return new ProductResponse(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteProductsById(@PathVariable String id) {
        productService.deleteProductById(id);
    }
}