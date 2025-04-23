package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.report.ReportDto;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<ReportDto> data = dataService.getDataByUnit(unitId).stream().map(item -> new ReportDto(item, formatDate)).collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("data", data);
        context.setVariable("unitName", unitName);
        context.setVariable("startDate", formatDate.formatToDate(start, false));
        context.setVariable("endDate", formatDate.formatToDate(end, false));

        String html = templateEngine.process("report", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();

        try {
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar o PDF");
        }

        return outputStream.toByteArray();
    }
}
