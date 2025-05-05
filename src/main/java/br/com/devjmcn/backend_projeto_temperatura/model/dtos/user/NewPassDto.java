package br.com.devjmcn.backend_projeto_temperatura.model.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewPassDto(
        @NotNull
        @NotBlank
        String currentPass,
        @NotNull
        @NotBlank
        String newPass
){}
