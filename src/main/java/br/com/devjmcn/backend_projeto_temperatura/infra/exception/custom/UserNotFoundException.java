package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
