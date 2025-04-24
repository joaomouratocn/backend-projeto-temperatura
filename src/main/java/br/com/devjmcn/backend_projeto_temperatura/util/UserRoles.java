package br.com.devjmcn.backend_projeto_temperatura.util;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRoles(String role){
        this.role = role;
    }

}
