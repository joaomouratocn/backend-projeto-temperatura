package br.com.devjmcn.backend_projeto_temperatura.model.dtos.report;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.GetDataByUnitResponse;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;

public record ReportDto(
        String dateFormatted,
        String refMin,
        String refCur,
        String refMax,
        String envMin,
        String envCur,
        String envMax,
        String userName
) {
    public ReportDto(GetDataByUnitResponse getDataByUnitResponse, FormatDate formatDate) {
        this(
                formatDate.formatToDate(getDataByUnitResponse.dateTime(), true),
                String.valueOf(getDataByUnitResponse.refMin()),
                String.valueOf(getDataByUnitResponse.refCur()),
                String.valueOf(getDataByUnitResponse.refMax()),
                String.valueOf(getDataByUnitResponse.envMin()),
                String.valueOf(getDataByUnitResponse.envCur()),
                String.valueOf(getDataByUnitResponse.envMax()),
                getDataByUnitResponse.userName()
        );
    }
}
