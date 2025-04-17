package br.com.devjmcn.backend_projeto_temperatura.repository;

import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {}
