package br.com.devjmcn.backend_projeto_temperatura.model.data.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetDataIntervalDto(
        UUID unitId,
        @NotNull
        long start,
        @NotNull
        long end
) {}
