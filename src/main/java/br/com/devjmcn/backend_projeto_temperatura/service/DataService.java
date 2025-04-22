package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NotUserFoundException;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.GetDataByUnitResponse;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.SaveResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.DataEntity;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UnitEntity;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.DataRepository;
import br.com.devjmcn.backend_projeto_temperatura.repository.UnitRepository;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UnitRepository unitRepository;

    public SaveResponseDto saveData(SaveDataDto saveDataDto) {
        UserEntity user = userRepository.findById(saveDataDto.userId())
                .orElseThrow(() -> new NotUserFoundException("User not found!!"));

        UnitEntity unit = unitRepository.findById(saveDataDto.unitId())
                .orElseThrow(() -> new NotUserFoundException("Unit not found!"));
        DataEntity newData = new DataEntity(
                user,
                unit,
                saveDataDto.refMin(),
                saveDataDto.refCur(),
                saveDataDto.refMax(),
                saveDataDto.envMin(),
                saveDataDto.envCur(),
                saveDataDto.envMax(),
                saveDataDto.dateTime()
        );

        dataRepository.save(newData);

        return new SaveResponseDto("OK", saveDataDto.dateTime());
    }

    public List<GetDataByUnitResponse> getDataByUnit(UUID unitId, long start, long end) {
        return dataRepository.getDataByUnit(unitId, start, end);
    }

    public List<GetDataByUnitResponse> getDataByUnit(UUID unitId) {
        long now = System.currentTimeMillis();
        long start = now - (5L * 24 * 60 * 60 * 1000);
        return dataRepository.getDataByUnit(unitId, start, now);
    }
}
