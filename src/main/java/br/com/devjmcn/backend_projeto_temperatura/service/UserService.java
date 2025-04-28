package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NotUserFoundException;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public RegisterResponseDto updateUser(RegisterDto registerDto) {
        UserEntity user = userRepository.findById(registerDto.id())
                .orElseThrow(() -> new NotUserFoundException("User not found!"));
        UserEntity updatedUser = new UserEntity(user.getId(), registerDto.name(), registerDto.username(), user.getPassword(), user.getUnit(), UserRoles.USER);
        userRepository.save(updatedUser);
        return new RegisterResponseDto("Salvo com sucesso!");
    }
}
