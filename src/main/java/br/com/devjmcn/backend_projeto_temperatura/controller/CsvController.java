package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.service.ExportCsvService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/export-csv")
public class CsvController {
    @Autowired
    ExportCsvService exportCsvService;

    @GetMapping
    public ResponseEntity<FileSystemResource> exportCsv(
            @RequestParam @Validated long start,
            @RequestParam @Validated long end,
            @RequestParam @Validated UUID unit
    ) {
        File file = exportCsvService.exportCsv(unit, start, end);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(file));
    }
}
