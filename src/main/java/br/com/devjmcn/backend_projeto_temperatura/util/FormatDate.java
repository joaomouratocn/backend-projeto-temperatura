package br.com.devjmcn.backend_projeto_temperatura.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
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

        if(withHour){
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        }else {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        return dateTime.format(formatter);
    }
}
