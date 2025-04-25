package br.com.devjmcn.backend_projeto_temperatura.model.data.dtos;

public record GetDataByUnitResponseDto(
        String dateTime,
        String refMin,
        String refCur,
        String refMax,
        String envMin,
        String envCur,
        String envMax,
        String userName
        ) {}
