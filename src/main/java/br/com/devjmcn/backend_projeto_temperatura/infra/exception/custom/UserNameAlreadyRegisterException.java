package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class UserNameAlreadyRegisterException extends RuntimeException {
    public UserNameAlreadyRegisterException(String message) {
        super(message);
    }
}
