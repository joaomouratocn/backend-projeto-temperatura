package br.com.devjmcn.backend_projeto_temperatura.model.data.dtos;

import java.util.UUID;

public record GetDataIntervalDto(
        UUID unitId,
        long start,
        long end
) {}
