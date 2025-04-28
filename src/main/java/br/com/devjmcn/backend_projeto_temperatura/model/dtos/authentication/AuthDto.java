package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthDto(
        @NotNull
        @NotBlank
        String username,
        @NotNull
        @NotBlank
        String password
){}
