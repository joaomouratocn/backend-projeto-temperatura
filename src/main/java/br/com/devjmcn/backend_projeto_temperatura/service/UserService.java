package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.AuthenticatedUserProvider;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticatedUserProvider authenticatedUserProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String updatePass(NewPassDto newPassDto) {
        UUID idAuthUser = authenticatedUserProvider.getUserId();

        UserEntity userEntity = userRepository.findById(idAuthUser).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        String passHash = passwordEncoder.encode(newPassDto.currentPass());

        if (passHash != userEntity.getPassword()) {
            throw new RuntimeException("Senha atual invalída");
        }

        userEntity.setPassword(passHash);

        return "Senha atualizada com sucesso";
    }
}
