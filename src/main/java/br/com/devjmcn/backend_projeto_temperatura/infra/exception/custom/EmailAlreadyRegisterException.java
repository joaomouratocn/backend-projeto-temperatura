package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class EmailAlreadyRegisterException extends RuntimeException {
    public EmailAlreadyRegisterException(String message) {
        super(message);
    }
}
