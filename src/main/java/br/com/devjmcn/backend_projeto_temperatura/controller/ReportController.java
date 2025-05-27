package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.ErrorResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.SecurityConfig;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.service.ExportCsvService;
import br.com.devjmcn.backend_projeto_temperatura.service.GeneratePdfService;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Relatórios", description = "Controlador para extração de relatórios")
public class ReportController {
    @Autowired
    FormatDate formatDate;

    @Autowired
    ExportCsvService exportCsvService;

    @Autowired
    GeneratePdfService generatePdfService;

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Gera relatório de uma unidade", description = "Metodo onde se passa um intervalo e UUID de uma unidade, então é gerado o relatório de apenas uma unidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error na geração do relatório",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<byte[]> generatePdfOneUnit(
            @RequestParam @Validated String start,
            @RequestParam @Validated String end,
            @RequestParam @Validated UUID unitid) {

        long convertedDateStart = formatDate.formatToMilli(start, false);
        long convertedDateEnd = formatDate.formatToMilli(end, true);

        GetDataIntervalDto received =
                new GetDataIntervalDto(
                        unitid,
                        convertedDateStart,
                        convertedDateEnd);

        byte[] pdf = generatePdfService.generateReport(received);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                .body(pdf);
    }




    @GetMapping(value = "/pdf-all-units", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Gera relatório de todas unidades", description = "Metodo onde se passa um intervalo, então é gerado o relatório de todas unidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error na geração do relatório",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<byte[]> generatePdfAllUnits(
            @RequestParam @Validated String start,
            @RequestParam @Validated String end
    ) {
        long convertedDateStart = formatDate.formatToMilli(start, false);
        long convertedDateEnd = formatDate.formatToMilli(end, true);

        GetDataIntervalDto received =
                new GetDataIntervalDto(
                        null,
                        convertedDateStart,
                        convertedDateEnd);

        byte[] pdf = generatePdfService.generateReportAll(received);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                .body(pdf);
    }





    @GetMapping("/csv")
    @Operation(summary = "Gera relatório de uma em formato CSV", description = "Metodo onde se passa um intervalo e UUID da unidade, então é gerado o relatório de apenas uma unidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error na geração do relatório",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<FileSystemResource> exportCsv(
            @RequestParam @Validated String start,
            @RequestParam @Validated String end,
            @RequestParam @Validated UUID unit
    ) {

        long convertedDateStart = formatDate.formatToMilli(start, false);
        long convertedDateEnd = formatDate.formatToMilli(end, true);

        File file = exportCsvService.exportCsv(unit, convertedDateStart, convertedDateEnd);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(file));
    }
}
