package br.andrevasques.spring_exercises.model.repositories;

import br.andrevasques.spring_exercises.model.entitites.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrderRepository extends MongoRepository<Order, String> {
    public Page<Order> findAllByClientId(String clientId, Pageable pageable);
}
