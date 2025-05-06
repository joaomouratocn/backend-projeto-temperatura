package br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom;

public class RegisterNewPasswordException extends RuntimeException {
    public RegisterNewPasswordException(String message) {
        super(message);
    }
}
