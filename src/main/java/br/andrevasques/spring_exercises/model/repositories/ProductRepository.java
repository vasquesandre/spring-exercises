package br.andrevasques.spring_exercises.model.repositories;

import br.andrevasques.spring_exercises.model.entitites.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {


}
