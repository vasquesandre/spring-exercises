package br.andrevasques.spring_exercises.dto.client;

import br.andrevasques.spring_exercises.model.entitites.Client;

public record ClientRequest (
        String id,
        String name,
        String cpf
) {
    public ClientRequest(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getCpf()
        );
    }
}
