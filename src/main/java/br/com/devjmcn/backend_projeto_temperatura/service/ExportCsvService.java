package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.GetDataByUnitResponse;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.report.ReportDto;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExportCsvService {
    @Autowired
    DataService dataService;
    @Autowired
    FormatDate formatDate;

    public File exportCsv(UUID unitId, long start, long end) {
        List<ReportDto> data = dataService.getDataByUnit(unitId)
                .stream()
                .map(item -> new ReportDto(item, formatDate)).toList();
        try {

            File tempFile = File.createTempFile("data", ".csv");

            try (
                    FileWriter writer = new FileWriter(tempFile);
                    CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                            .withDelimiter(';')
                            .withHeader("date", "refmin", "refcur", "refmax", "envmin", "envcur", "envmax", "username"))
            ) {


                for (ReportDto item : data) {
                    csvPrinter.printRecord(item.dateFormatted());
                    csvPrinter.printRecord(item.refMin());
                    csvPrinter.printRecord(item.refCur());
                    csvPrinter.printRecord(item.refMax());
                    csvPrinter.printRecord(item.envMin());
                    csvPrinter.printRecord(item.envCur());
                    csvPrinter.printRecord(item.envMax());
                    csvPrinter.printRecord(item.userName());
                }
                csvPrinter.flush();
            }
            return tempFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
