package br.com.devjmcn.backend_projeto_temperatura.model.data.dtos;

import jakarta.validation.constraints.NotNull;

public record SaveDataDto(
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
