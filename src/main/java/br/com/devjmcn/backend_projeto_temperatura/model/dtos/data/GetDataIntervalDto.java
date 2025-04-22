package br.com.devjmcn.backend_projeto_temperatura.model.dtos.data;

import java.util.UUID;

public record GetDataIntervalDto(
        UUID unitId,
        long start,
        long end
) {}
