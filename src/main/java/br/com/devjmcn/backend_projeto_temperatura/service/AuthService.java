package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.security.TokenService;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Value("${api.secret.pass.default}")
    private String passDefault;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponseDto auth(AuthDto authDto) {
        try {
            String normalizedUsername = authDto.username().toUpperCase();

            UsernamePasswordAuthenticationToken userAndPass =
                    new UsernamePasswordAuthenticationToken(normalizedUsername, authDto.password());

            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            UserEntity user = (UserEntity) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);

            boolean mustPass = passDefault.equals(authDto.password());

            return new AuthResponseDto(user.getName(), token, mustPass);
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
