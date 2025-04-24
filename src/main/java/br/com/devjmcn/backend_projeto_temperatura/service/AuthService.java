package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.EmailAlreadyRegisterException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.TokenService;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AuthResponseDto auth(AuthDto authDto) {
        try {
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());

            Authentication authenticate = authenticationManager.authenticate(auth);

            String token = tokenService.generateToken((UserEntity) authenticate.getPrincipal());

            return new AuthResponseDto(auth.getName(), token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RegisterResponseDto register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.email()).isPresent()) {
            throw new EmailAlreadyRegisterException("Email already exists");
        }

        String encryptedPassword = passwordEncoder.encode(registerDto.password());

        UserEntity newUser = new UserEntity(
                registerDto.name(),
                registerDto.email(),
                encryptedPassword,
                registerDto.unit(),
                registerDto.role()
        );

        userRepository.save(newUser);

        return new RegisterResponseDto(newUser.getName(), newUser.getEmail());
    }
}
