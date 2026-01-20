package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.client.ClientRequest;
import br.andrevasques.spring_exercises.dto.client.CreateClientRequest;
import br.andrevasques.spring_exercises.dto.client.UpdateClientRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping
    public ClientRequest save(@RequestBody @Valid CreateClientRequest dto) {
        Client client = new Client(dto.name(), dto.cpf());
        Client saved = clientRepository.save(client);
        return new ClientRequest(
                saved.getId(),
                saved.getName(),
                saved.getCpf()
        );
    }

    @GetMapping
    public Page<ClientRequest> getClients() {
        Pageable pageable = PageRequest.of(0, 10);
        return clientRepository.findAll(pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @GetMapping("/{id}")
    public ClientRequest getClientById(@PathVariable Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
        return new ClientRequest(
                client.getId(),
                client.getName(),
                client.getCpf()
        );
    }

    @GetMapping("/name")
    public Page<ClientRequest> getClientsByName(@RequestParam String name) {
        Pageable pageable = PageRequest.of(0, 10);
        return clientRepository.findByNameContaining(name, pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Page<ClientRequest> getClientsByPage(@PathVariable int pageNumber, @PathVariable int pageSize) {
        if(pageSize >= 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return clientRepository.findAll(pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @PatchMapping("/{id}")
    public ClientRequest update(@PathVariable Integer id, @RequestBody UpdateClientRequest dto) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
        client.update(dto.name(), dto.cpf());
        Client saved = clientRepository.save(client);
        return new ClientRequest(
                saved.getId(),
                saved.getName(),
                saved.getCpf()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found"));
        clientRepository.delete(client);
    }
}
