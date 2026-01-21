package br.andrevasques.spring_exercises.service;

import br.andrevasques.spring_exercises.dto.client.CreateClientRequest;
import br.andrevasques.spring_exercises.dto.client.UpdateClientRequest;
import br.andrevasques.spring_exercises.model.entitites.Client;
import br.andrevasques.spring_exercises.model.entitites.Order;
import br.andrevasques.spring_exercises.model.repositories.ClientRepository;
import br.andrevasques.spring_exercises.model.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public ClientService(ClientRepository clientRepository, OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    private Client findClientByIdOrThrow(String clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Client not found using ID"));
    }

    private Page<Client> findClientByCpfOrThrow(String cpf, Pageable pageable) {
        return clientRepository.findByCpfContaining(cpf, pageable);
    }

    private void checkIfThereIsAnOrderWithClientIdForClientDeletionOrThrow(String clientId) {
        if(orderRepository.existsOrderByClientId(clientId)) {
            throw new ResponseStatusException(CONFLICT, "There is already an order with that client id");
        }
    }

    public Client saveClient(CreateClientRequest dto) {
        Client client = new Client(dto.name(), dto.cpf());
        return clientRepository.save(client);
    }

    public Page<Client> getClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getClientById(String clientId) {
        return findClientByIdOrThrow(clientId);
    }

    public Page<Client> getClientsByNameContaining(String name, Pageable pageable) {
        return clientRepository.findByNameContaining(name, pageable);
    }

    public Page<Client> getClientsByCpfContaining(String cpf, Pageable pageable) {
        return findClientByCpfOrThrow(cpf, pageable);
    }

    public Client update(String id, UpdateClientRequest dto) {
        Client client = findClientByIdOrThrow(id);
        client.update(dto.name(), dto.cpf());
        return clientRepository.save(client);
    }

    public void deleteClientById(String id) {
        checkIfThereIsAnOrderWithClientIdForClientDeletionOrThrow(id);
        Client client = findClientByIdOrThrow(id);
        clientRepository.delete(client);
    }
}
