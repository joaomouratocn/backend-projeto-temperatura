package br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthResponseDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String token,
        @NotNull
        boolean mustChange
){}
