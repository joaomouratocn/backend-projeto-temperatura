package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class NotUserFoundException extends RuntimeException{
    public NotUserFoundException(String message) {
        super(message);
    }
}
