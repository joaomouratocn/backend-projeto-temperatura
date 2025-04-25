package br.com.devjmcn.backend_projeto_temperatura.infra.security;

import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticatedUserProvider {
    public UUID getUserId(){
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntity.getId();
    }
}
