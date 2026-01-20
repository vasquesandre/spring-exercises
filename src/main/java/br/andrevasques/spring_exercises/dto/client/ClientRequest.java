package br.andrevasques.spring_exercises.dto.client;

public record ClientRequest (
        Integer id,
        String name,
        String cpf
)
{ }
