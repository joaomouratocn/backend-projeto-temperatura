package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
