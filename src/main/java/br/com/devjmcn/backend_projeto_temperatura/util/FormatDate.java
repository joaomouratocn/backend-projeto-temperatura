package br.com.devjmcn.backend_projeto_temperatura.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class FormatDate {

    public String formatToDate(long dateLong, Boolean withHour) {
        DateTimeFormatter formatter;

        LocalDateTime dateTime = Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if (withHour) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        } else {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        return dateTime.format(formatter);
    }

    public long formatToMilli(String date, boolean endOfDay) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate localDate = LocalDate.parse(date, inputFormatter);

        LocalDateTime localDateTime = endOfDay
                ? localDate.atTime(23, 59, 59)   // Se for fim do dia
                : localDate.atStartOfDay();      // Se for começo do dia

        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
