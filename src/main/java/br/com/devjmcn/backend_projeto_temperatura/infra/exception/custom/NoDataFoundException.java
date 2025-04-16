package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException(String message){
        super(message);
    }
}
