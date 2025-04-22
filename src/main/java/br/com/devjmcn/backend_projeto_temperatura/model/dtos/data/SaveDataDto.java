package br.com.devjmcn.backend_projeto_temperatura.model.dtos.data;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaveDataDto(
        UUID id,
        @NotNull
        UUID userId,
        @NotNull
        UUID unitId,
        @NotNull
        double refMin,
        @NotNull
        double refCur,
        @NotNull
        double refMax,
        @NotNull
        double envMin,
        @NotNull
        double envCur,
        @NotNull
        double envMax,
        @NotNull
        long dateTime
){}
