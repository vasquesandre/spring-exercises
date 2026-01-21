package br.andrevasques.spring_exercises.controller;

import br.andrevasques.spring_exercises.dto.client.ClientResponse;
import br.andrevasques.spring_exercises.dto.client.CreateClientRequest;
import br.andrevasques.spring_exercises.dto.client.UpdateClientRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ClientResponse save(@RequestBody @Valid CreateClientRequest dto) {
        Client saved = clientService.saveClient(dto);
        return new ClientResponse(saved);
    }

    @GetMapping
    public Page<ClientResponse> getClients(Pageable pageable) {
        return clientService.getClients(pageable)
                .map(ClientResponse::new);
    }

    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable String id) {
        Client client = clientService.getClientById(id);
        return new ClientResponse(client);
    }

    @GetMapping("/name")
    public Page<ClientResponse> getClientsByName(@RequestParam String name, Pageable pageable) {
        return clientService.getClientsByNameContaining(name, pageable)
                .map(ClientResponse::new);
    }

    @GetMapping("/cpf")
    public Page<ClientResponse> getClientsByCpf(@RequestParam String cpf, Pageable pageable) {
        return clientService.getClientsByCpfContaining(cpf, pageable)
                .map(ClientResponse::new);
    }

    @PatchMapping("/{id}")
    public ClientResponse update(@PathVariable String id, @RequestBody UpdateClientRequest dto) {
        Client saved = clientService.updateClientById(id, dto);
        return new ClientResponse(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        clientService.deleteClientById(id);
    }
}
