package br.com.devjmcn.backend_projeto_temperatura.util;

import br.com.devjmcn.backend_projeto_temperatura.infra.security.PasswordHashGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupHashPrinter {
    @Value("${api.secret.pass.default}")
    private String passDefault;

    @Bean
    public CommandLineRunner printHash(PasswordHashGenerator hashGenerator) {
        return args -> {
            String hash = hashGenerator.generateHash(passDefault);
            System.out.println("=======================================");
            System.out.println("Generate hash: " + hash);
            System.out.println("=======================================");
        };
    }
}
