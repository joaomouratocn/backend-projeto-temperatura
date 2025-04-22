package br.com.devjmcn.backend_projeto_temperatura.model.dtos.user;

public record AuthUserResponseDto(
        String name,
        String token,
        String exp
){}
