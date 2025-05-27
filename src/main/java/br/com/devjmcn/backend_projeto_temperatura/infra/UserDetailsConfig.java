package br.com.devjmcn.backend_projeto_temperatura.infra;

import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username.toUpperCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
