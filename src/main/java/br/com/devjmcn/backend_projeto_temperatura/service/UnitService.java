package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NoDataFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UnitNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UnitEntity;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UnitRepository;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UserRepository userRepository;

    public List<UnitDto> getAllUnits(){
        List<UnitEntity> unitEntityList = unitRepository.findAll();
        if(unitEntityList.isEmpty()){
            throw new NoDataFoundException("No units found");
        }

        return unitEntityList.stream()
                .map(unitEntity -> new UnitDto(unitEntity.getId(), unitEntity.getName()))
                .collect(Collectors.toList());
    }

    public UnitDto getUnit(UUID id){
         return unitRepository.findById(id)
                 .map(unitEntity -> new UnitDto(unitEntity.getId(), unitEntity.getName()))
                 .orElseThrow(() -> new NoDataFoundException("No Unit Found!"));
    }

    public UnitDto getUnitByUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UnitEntity unit = unitRepository.findById(userEntity.getUnit()).orElseThrow(() -> new UnitNotFoundException("Unit not found"));

        return new UnitDto(unit.getId(), unit.getName());
    }
}
