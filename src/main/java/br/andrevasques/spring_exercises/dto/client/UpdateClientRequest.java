package br.andrevasques.spring_exercises.dto.client;

public record UpdateClientRequest (
        String name,
        String cpf
)
{ }
