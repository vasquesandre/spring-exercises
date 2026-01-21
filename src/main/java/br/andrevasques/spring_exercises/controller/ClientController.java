package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.client.ClientRequest;
import br.andrevasques.spring_exercises.dto.client.CreateClientRequest;
import br.andrevasques.spring_exercises.dto.client.UpdateClientRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import br.andrevasques.spring_exercises.model.repositories.OrderRepository;
import br.andrevasques.spring_exercises.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ClientRequest save(@RequestBody @Valid CreateClientRequest dto) {
        Client saved = clientService.saveClient(dto);
        return new ClientRequest(
                saved.getId(),
                saved.getName(),
                saved.getCpf()
        );
    }

    @GetMapping
    public Page<ClientRequest> getClients() {
        Pageable pageable = PageRequest.of(0, 10);
        return clientService.getClients(pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @GetMapping("/{id}")
    public ClientRequest getClientById(@PathVariable String id) {
        Client client = clientService.getClientById(id);
        return new ClientRequest(
                client.getId(),
                client.getName(),
                client.getCpf()
        );
    }

    @GetMapping("/name")
    public Page<ClientRequest> getClientsByName(@RequestParam String name) {
        Pageable pageable = PageRequest.of(0, 10);
        return clientService.getClientsByNameContaining(name, pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @GetMapping("/cpf")
    public Page<ClientRequest> getClientsByCpf(@RequestParam String cpf) {
        Pageable pageable = PageRequest.of(0, 10);
        return clientService.getClientsByCpfContaining(cpf, pageable)
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
        return clientService.getClients(pageable)
                .map(client -> new ClientRequest(
                        client.getId(),
                        client.getName(),
                        client.getCpf()
                ));
    }

    @PatchMapping("/{id}")
    public ClientRequest update(@PathVariable String id, @RequestBody UpdateClientRequest dto) {
        Client saved = clientService.update(id, dto);
        return new ClientRequest(
                saved.getId(),
                saved.getName(),
                saved.getCpf()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        clientService.deleteClientById(id);
    }
}
