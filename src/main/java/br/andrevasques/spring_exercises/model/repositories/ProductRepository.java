package br.andrevasques.spring_exercises.model.repositories;

import br.andrevasques.spring_exercises.model.entitites.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public Page<Product> findByNameContaining(String name, Pageable pageable);
}
