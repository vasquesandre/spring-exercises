package br.andrevasques.spring_exercises.dto.client;

import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest (
        @NotBlank String name,
        @NotBlank String cpf
)
{ }
