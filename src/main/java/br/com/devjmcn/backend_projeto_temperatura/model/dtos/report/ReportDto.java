package br.com.devjmcn.backend_projeto_temperatura.model.dtos.report;

import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;

public record ReportDto(
        String date,
        String refMin,
        String refCur,
        String refMax,
        String envMin,
        String envCur,
        String envMax,
        String username
) {
    public ReportDto(GetDataByUnitResponseDto getDataByUnitResponseDto, FormatDate formatDate) {
        this(
                getDataByUnitResponseDto.dateTime(),
                getDataByUnitResponseDto.refMin(),
                getDataByUnitResponseDto.refCur(),
                getDataByUnitResponseDto.refMax(),
                getDataByUnitResponseDto.envMin(),
                getDataByUnitResponseDto.envCur(),
                getDataByUnitResponseDto.envMax(),
                getDataByUnitResponseDto.userName()
        );
    }
}
