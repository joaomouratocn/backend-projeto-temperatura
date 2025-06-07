package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.report.ReportDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.repository.DataRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExportCsvService {
    @Autowired
    DataRepository dataRepository;
    @Autowired
    FormatDate formatDate;
    @Autowired
    UnitService unitService;

    public File exportCsv(long start, long end) {
        List<UnitDto> unitList = unitService.getAllUnits();

        try {
            File tempFile = File.createTempFile("data", ".csv");

            try (
                    FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter writer = new BufferedWriter(outputStreamWriter)
            ) {
                writer.write('\uFEFF');

                CSVPrinter csvPrinter = new CSVPrinter(
                        writer,
                        CSVFormat.Builder.create().setDelimiter(';').build()
                );
                csvPrinter.printRecord("Relatório de todas unidade no intervalo de:", start, "a", end);
                csvPrinter.println();

                for (UnitDto unit : unitList) {
                    csvPrinter.printRecord("Unidade:", unit.name());
                    csvPrinter.printRecord("UUID:", unit.uuid());
                    csvPrinter.printRecord("Data", "Geladeira Miníma", "Geladeira Atual", "Geladeira Maxíma",
                            "Ambiente Miníma", "Ambiente Atual", "Ambiente Maxíma", "Usuário");

                    List<ReportDto> data = dataRepository.getDataByUnit(unit.uuid(), start, end).stream()
                            .map(item -> new ReportDto(
                                    new GetDataByUnitResponseDto(
                                            String.valueOf(item.getDateTime()),
                                            String.valueOf(item.getRefMin()),
                                            String.valueOf(item.getRefCur()),
                                            String.valueOf(item.getRefMax()),
                                            String.valueOf(item.getEnvMin()),
                                            String.valueOf(item.getEnvCur()),
                                            String.valueOf(item.getEnvMax()),
                                            item.getUserName()),
                                    formatDate)
                            )
                            .toList();

                    for (ReportDto item : data) {
                        csvPrinter.printRecord(
                                item.date(),
                                item.refMin(),
                                item.refCur(),
                                item.refMax(),
                                item.envMin(),
                                item.envCur(),
                                item.envMax(),
                                item.username()
                        );
                    }
                    csvPrinter.println();
                }

                csvPrinter.flush();
                return tempFile;

            } catch (IOException ex) {
                throw new RuntimeException("Erro ao escrever o CSV", ex);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o arquivo temporário", e);
        }
    }

}
