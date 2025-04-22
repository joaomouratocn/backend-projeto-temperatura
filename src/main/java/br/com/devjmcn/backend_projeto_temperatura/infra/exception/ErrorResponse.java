package br.com.devjmcn.backend_projeto_temperatura.infra.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int statusCode;
    private final String message;
    private final long timestamp = System.currentTimeMillis();

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
