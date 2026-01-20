package br.andrevasques.spring_exercises.dto.client;

public record ClientRequest (
        String id,
        String name,
        String cpf
)
{ }
