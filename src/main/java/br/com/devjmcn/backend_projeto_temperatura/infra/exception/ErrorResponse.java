package br.com.devjmcn.backend_projeto_temperatura.infra.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final int statusCode;
    private final String message;
    private final String timestamp = LocalDateTime.now().toString();

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
