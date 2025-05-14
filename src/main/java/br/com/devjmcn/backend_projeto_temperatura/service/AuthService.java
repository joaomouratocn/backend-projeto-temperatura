package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNameAlreadyRegisterException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.PasswordHashGenerator;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.TokenService;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.ClearText;
import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Value("${api.secret.pass.default}")
    private String passDefault;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordHashGenerator passwordHashGenerator;

    @Autowired
    TokenService tokenService;

    @Autowired
    ClearText clearText;

    private final AuthenticationConfiguration authenticationConfiguration;

    public AuthService(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AuthResponseDto auth(AuthDto authDto) {
        try {
            boolean mustPass = false;

            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authDto.username().toUpperCase(), authDto.password());

            Authentication authenticate = authenticationManager.authenticate(auth);

            String token = tokenService.generateToken((UserEntity) authenticate.getPrincipal());

            if (passDefault.equals(authDto.password())) {
                mustPass = true;
            }

            return new AuthResponseDto(((UserEntity) authenticate.getPrincipal()).getName(), token, mustPass);
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RegisterResponseDto register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()).isPresent()) {
            throw new UserNameAlreadyRegisterException("Usuário já cadastrado");
        }

        String encryptedPassword = passwordHashGenerator.generateHash(passDefault);

        UserEntity newUser = new UserEntity(
                clearText.normalizeText(registerDto.name().toUpperCase()),
                clearText.normalizeText(registerDto.username().toUpperCase()),
                encryptedPassword,
                registerDto.unit(),
                UserRoles.USER
        );

        userRepository.save(newUser);

        return new RegisterResponseDto("Cadastrado com sucesso");
    }
}
