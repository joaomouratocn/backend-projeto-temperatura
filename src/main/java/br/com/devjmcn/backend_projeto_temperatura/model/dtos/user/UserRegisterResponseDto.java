package br.com.devjmcn.backend_projeto_temperatura.model.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterResponseDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String email
){}
