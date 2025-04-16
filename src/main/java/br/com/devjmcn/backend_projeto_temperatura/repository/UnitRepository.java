package br.com.devjmcn.backend_projeto_temperatura.repository;

import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<UnitEntity, UUID> {}
