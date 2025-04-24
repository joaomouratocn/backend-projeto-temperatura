package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthDto(
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String password
){}
