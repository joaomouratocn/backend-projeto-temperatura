package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class UnitNotFoundException extends RuntimeException{
    public UnitNotFoundException(String message) {
        super(message);
    }
}
