package br.andrevasques.spring_exercises.model.repositories;

import br.andrevasques.spring_exercises.model.entitites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Integer> {

    public Page<Product> findByNameContaining(String name, Pageable pageable);
}
