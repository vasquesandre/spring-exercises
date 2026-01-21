package br.andrevasques.spring_exercises.service;

import br.andrevasques.spring_exercises.dto.product.CreateProductRequest;
import br.andrevasques.spring_exercises.dto.product.UpdateProductRequest;
import br.andrevasques.spring_exercises.model.entitites.Product;
import br.andrevasques.spring_exercises.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private Product findProductByIdOrThrow(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found"));
    }

    public Product createProduct(CreateProductRequest dto) {
        Product product = Product.create(
                dto.name(),
                dto.price(),
                dto.discount()
        );

        return productRepository.save(product);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(String productId) {
        return findProductByIdOrThrow(productId);
    }

    public Page<Product> getProductsByNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    public Product updateProductById(String id, UpdateProductRequest dto) {
        Product product = findProductByIdOrThrow(id);
        product.update(dto.name(), dto.price(), dto.discount());

        return productRepository.save(product);
    }

    public void deleteProductById(String id) {
        Product product = findProductByIdOrThrow(id);
        productRepository.delete(product);
    }
}
