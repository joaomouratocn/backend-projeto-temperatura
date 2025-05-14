package br.com.devjmcn.backend_projeto_temperatura.infra.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashGenerator {
    private final PasswordEncoder passwordEncoder;

    public PasswordHashGenerator(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public String generateHash(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }
}
