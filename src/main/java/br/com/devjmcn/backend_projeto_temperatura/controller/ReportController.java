package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.service.GeneratePdfService;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    GeneratePdfService generatePdfService;

    @Autowired
    FormatDate formatDate;

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(
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
}
