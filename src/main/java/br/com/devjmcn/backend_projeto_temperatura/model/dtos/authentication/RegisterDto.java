package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String username,
        @NotNull
        UUID unituuid
) {}
