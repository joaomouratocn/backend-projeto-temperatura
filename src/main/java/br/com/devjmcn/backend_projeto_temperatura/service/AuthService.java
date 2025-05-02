package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNameAlreadyRegisterException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.TokenService;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    private final AuthenticationConfiguration authenticationConfiguration;

    public AuthService(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AuthResponseDto auth(AuthDto authDto) {
        try {
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authDto.username(), authDto.password());

            Authentication authenticate = authenticationManager.authenticate(auth);

            String token = tokenService.generateToken((UserEntity) authenticate.getPrincipal());

            return new AuthResponseDto(((UserEntity) authenticate.getPrincipal()).getName(), token);
        } catch (BadCredentialsException e) {
            throw e;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RegisterResponseDto register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()).isPresent()) {
            throw new UserNameAlreadyRegisterException("Usuário já cadastrado");
        }

        String encryptedPassword = passwordEncoder.encode("A8O3J0S2");

        UserEntity newUser = new UserEntity(
                registerDto.name(),
                registerDto.username(),
                encryptedPassword,
                registerDto.unit(),
                UserRoles.USER
        );

        userRepository.save(newUser);

        return new RegisterResponseDto("Cadastrado com sucesso");
    }
}
