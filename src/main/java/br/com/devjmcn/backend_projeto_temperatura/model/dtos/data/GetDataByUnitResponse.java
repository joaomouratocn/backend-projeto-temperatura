package br.com.devjmcn.backend_projeto_temperatura.model.dtos.data;

public record GetDataByUnitResponse(
        long dateTime,
        double refMin,
        double refCur,
        double refMax,
        double envMin,
        double envCur,
        double envMax,
        String userName
) {
}
