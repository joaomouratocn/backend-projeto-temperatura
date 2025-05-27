package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.SuccessResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNameAlreadyRegisterException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.AuthenticatedUserProvider;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.ClearText;
import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Value("${api.secret.pass.default}")
    private String passDefault;

    @Autowired
    ClearText clearText;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticatedUserProvider authenticatedUserProvider;

    public SuccessResponse updatePass(NewPassDto newPassDto) {
        UUID idAuthUser = authenticatedUserProvider.getUserId();

        UserEntity userEntity = userRepository.findById(idAuthUser).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        boolean matches = passwordEncoder.matches(newPassDto.currentPass(), userEntity.getPassword());

        if (!matches) {
            throw new RuntimeException("Senha atual invalída");
        }

        String passHash = passwordEncoder.encode(newPassDto.newPass());

        userEntity.setPassword(passHash);

        userRepository.save(userEntity);

        return new SuccessResponse("Senha atualizada com sucesso");
    }

    public SuccessResponse register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()).isPresent()) {
            throw new UserNameAlreadyRegisterException("Usuário já cadastrado");
        }

        String encryptedPassword = passwordEncoder.encode(passDefault);

        UserEntity newUser = new UserEntity(
                clearText.normalizeText(registerDto.name().toUpperCase()),
                clearText.normalizeText(registerDto.username().toUpperCase()),
                encryptedPassword,
                registerDto.unituuid(),
                UserRoles.USER
        );

        userRepository.save(newUser);

        return new SuccessResponse("Cadastrado com sucesso");
    }
}
