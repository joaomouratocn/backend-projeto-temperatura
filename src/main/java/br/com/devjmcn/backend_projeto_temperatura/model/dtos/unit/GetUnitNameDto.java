package br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetUnitNameDto(
        @NotNull
        @NotBlank
        String name
){}
