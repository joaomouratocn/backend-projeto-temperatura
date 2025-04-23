package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.GetDataByUnitResponse;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.report.ReportDto;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

        try{
            File tempFile = File.createTempFile("data", ".csv");

            try (
                    FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter writer = new BufferedWriter(outputStreamWriter)
            ) {
                writer.write('\uFEFF');

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                        .withDelimiter(';')
                        .withHeader("Data", "Geladeira Miníma", "Geladeira Atual", "Geladeira Maxíma", "Ambiente Miníma", "Ambiente Atual", "Ambiente Maxíma", "Usuário"));

                for (ReportDto item : data) {
                    csvPrinter.printRecord(
                            item.dateFormatted(),
                            item.refMin(),
                            item.refCur(),
                            item.refMax(),
                            item.envMin(),
                            item.envCur(),
                            item.envMax(),
                            item.userName()
                    );
                }
                csvPrinter.flush();
                return tempFile;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
