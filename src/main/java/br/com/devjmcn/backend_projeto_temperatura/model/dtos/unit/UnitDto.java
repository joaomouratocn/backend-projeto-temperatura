package br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnitDto(
     @NotNull
     @NotBlank
     UUID id,
     @NotNull
     @NotBlank
     String name
) {}
