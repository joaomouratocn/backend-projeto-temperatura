package br.com.devjmcn.backend_projeto_temperatura.util;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class ClearText {
    public String normalizeText(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
