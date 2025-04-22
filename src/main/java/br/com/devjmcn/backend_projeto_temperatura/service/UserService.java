package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.IncorrectPasswordException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NotUserFoundException;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.AuthUserDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.AuthUserResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.SaveUserDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.SaveUserResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    //    @Autowired
    //    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    public SaveUserResponseDto save(SaveUserDto saveUserDto) {
        if (saveUserDto.id() == null) {
            try {
                //String passHash = passwordEncoder.encode(userRegisterDto.password());
                UserEntity newUser = new UserEntity(
                        saveUserDto.name(),
                        saveUserDto.email(),
                        saveUserDto.password(),
                        saveUserDto.unit()
                );
                userRepository.save(newUser);
                return new SaveUserResponseDto(newUser.getName(), newUser.getEmail());
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Email já cadastrado!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            UserEntity user = userRepository.findById(saveUserDto.id())
                    .orElseThrow(() -> new NotUserFoundException("User not found!"));
            UserEntity updatedUser = new UserEntity(user.getId(), saveUserDto.name(), saveUserDto.email(), user.getPassword(), user.getUnit());
            userRepository.save(updatedUser);
            return new SaveUserResponseDto(updatedUser.getName(), updatedUser.getEmail());
        }
    }

    public AuthUserResponseDto auth(AuthUserDto authUserDto) {
        UserEntity userEntity = userRepository.findByEmail(authUserDto.email())
                .orElseThrow(() -> new NotUserFoundException("User not found!"));
        if (!userEntity.getPassword().equals(authUserDto.password())){
            throw new IncorrectPasswordException("Incorrect password!");
        }
        //CRIAR TOKEN
        return new AuthUserResponseDto(userEntity.getName(), "token123", "31/12/2025");
    }
}
