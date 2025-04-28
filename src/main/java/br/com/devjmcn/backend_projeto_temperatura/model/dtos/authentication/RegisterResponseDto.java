package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterResponseDto(
        @NotNull
        @NotBlank
        String message
) {}
