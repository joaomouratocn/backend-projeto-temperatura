package br.com.devjmcn.backend_projeto_temperatura.model.dtos.data;

public record TemperatureDataDto(
        String unitName,
        String startDate,
        String endDate,
        String fridgeMin,
        String fridgeCur,
        String fridgeMax,
        String envMin,
        String envCur,
        String envMax
){}
