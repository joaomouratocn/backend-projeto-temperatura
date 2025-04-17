package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.UserRegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.UserRegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    public UserRegisterResponseDto register(UserRegisterDto userRegisterDto){
        try{
            String passHash = passwordEncoder.encode(userRegisterDto.password());
            UserEntity newUser = new UserEntity(
                    userRegisterDto.name(),
                    userRegisterDto.email(),
                    passHash,
                    userRegisterDto.unit()
            );

            userRepository.save(newUser);

            return new UserRegisterResponseDto(newUser.getName(), newUser.getEmail());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email já cadastrado!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
