package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.report.ReportDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class GeneratePdfService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private DataService dataService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private FormatDate formatDate;


    public byte[] generateReport(UUID unitId, long start, long end) {
        String unitName = unitService.getUnit(unitId).name();
        List<ReportDto> data = dataService.getDataByUnit(unitId, start, end).stream().map(item -> new ReportDto(item, formatDate)).toList();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Context context = new Context();
        context.setVariable("unitName", unitName);
        context.setVariable("startDate", formatDate.formatToDate(start, false));
        context.setVariable("endDate", formatDate.formatToDate(end, false));
        context.setVariable("generationTime", formatDate.formatToDate(System.currentTimeMillis(), true));
        if (!data.isEmpty()) {
            context.setVariable("data", data);
        }
        context.setVariable("contentFragment", data.isEmpty() ? "no-data" : "data");


        String html = templateEngine.process("report", context);

        try {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }

        return outputStream.toByteArray();
    }

    public byte[] generateReport(long start, long end) {
        List<UUID> unitIdList = unitService.getAllUnits().stream().map(UnitDto::id).toList();

        PDFMergerUtility merger = new PDFMergerUtility();
        ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
        merger.setDestinationStream(finalOutput);

        for (UUID unitId : unitIdList) {
            String unitName = unitService.getUnit(unitId).name();
            List<ReportDto> data = dataService.getDataByUnit(unitId, start, end)
                    .stream()
                    .map(item -> new ReportDto(item, formatDate))
                    .toList();

            Context context = new Context();
            context.setVariable("unitName", unitName);
            context.setVariable("startDate", formatDate.formatToDate(start, false));
            context.setVariable("endDate", formatDate.formatToDate(end, false));
            context.setVariable("generationTime", formatDate.formatToDate(System.currentTimeMillis(), true));
            if (!data.isEmpty()) {
                context.setVariable("data", data);
            }
            context.setVariable("contentFragment", data.isEmpty() ? "no-data" : "data");

            String html = templateEngine.process("report", context);

            try (ByteArrayOutputStream unitOutput = new ByteArrayOutputStream()) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(html, null);
                builder.toStream(unitOutput);
                builder.run();

                InputStream pdfStream = new ByteArrayInputStream(unitOutput.toByteArray());
                merger.addSource(pdfStream);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao gerar ou mesclar PDF", e);
            }
        }

        try {
            merger.mergeDocuments(null);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao finalizar PDF", e);
        }

        return finalOutput.toByteArray();
    }

}
