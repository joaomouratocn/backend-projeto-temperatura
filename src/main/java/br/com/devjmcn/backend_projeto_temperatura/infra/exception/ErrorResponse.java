package br.com.devjmcn.backend_projeto_temperatura.infra.exception;

public record ErrorResponse(int statusCode, String message) {}
