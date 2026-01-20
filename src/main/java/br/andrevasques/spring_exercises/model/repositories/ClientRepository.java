package br.andrevasques.spring_exercises.model.repositories;

import br.andrevasques.spring_exercises.model.entitites.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Page<Client> findByNameContaining(String name, Pageable pageable);
}
