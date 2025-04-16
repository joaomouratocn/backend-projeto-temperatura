package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NoDataFoundException;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UnitEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnitService {
    @Autowired
    UnitRepository unitRepository;

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
}
