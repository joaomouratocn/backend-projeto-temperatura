package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterDto(
        UUID id,
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String password,
        @NotNull
        UUID unit,
        @NotNull
        UserRoles role
) {}
