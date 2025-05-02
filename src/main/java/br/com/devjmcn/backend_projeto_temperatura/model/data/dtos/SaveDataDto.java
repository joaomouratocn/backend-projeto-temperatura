package br.com.devjmcn.backend_projeto_temperatura.model.data.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaveDataDto(
        @NotNull
        long dateTime,
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
        double envMax
) {
}
