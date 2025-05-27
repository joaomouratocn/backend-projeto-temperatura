package br.com.devjmcn.backend_projeto_temperatura.service;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.SuccessResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UnitNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.UserNotFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.AuthenticatedUserProvider;
import br.com.devjmcn.backend_projeto_temperatura.model.data.DataEntity;
import br.com.devjmcn.backend_projeto_temperatura.model.data.GetDataByUnit;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UnitEntity;
import br.com.devjmcn.backend_projeto_temperatura.model.entitys.UserEntity;
import br.com.devjmcn.backend_projeto_temperatura.repository.DataRepository;
import br.com.devjmcn.backend_projeto_temperatura.repository.UnitRepository;
import br.com.devjmcn.backend_projeto_temperatura.repository.UserRepository;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    AuthenticatedUserProvider authenticatedUserProvider;
    @Autowired
    FormatDate formatDate;

    public SuccessResponse saveData(SaveDataDto saveDataDto) {
        UUID userId = authenticatedUserProvider.getUserId();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isAdmin = userEntity.getRole() == UserRoles.ADMIN;
        boolean isSameUnit = userEntity.getUnit().equals(saveDataDto.unituuid());

        if (!isAdmin && !isSameUnit) {
            throw new RuntimeException("Usuário não tem permissão para inserir os dados nesta unidade");
        }

        UnitEntity unitEntity = unitRepository.findById(saveDataDto.unituuid())
                .orElseThrow(() -> new UnitNotFoundException("Unit not found!"));

        DataEntity newData = new DataEntity(
                userEntity,
                unitEntity,
                saveDataDto.refMin(),
                saveDataDto.refCur(),
                saveDataDto.refMax(),
                saveDataDto.envMin(),
                saveDataDto.envCur(),
                saveDataDto.envMax(),
                saveDataDto.dateTime()
        );

        dataRepository.save(newData);
        return new SuccessResponse("Dados enviados com sucesso");
    }

    public List<GetDataByUnitResponseDto> getDataByUnit(GetDataIntervalDto getDataIntervalDto) {
        List<GetDataByUnit> getDataByUnitList = dataRepository.getDataByUnit(
                getDataIntervalDto.unitId(),
                getDataIntervalDto.start(),
                getDataIntervalDto.end());
        return toGetDataByUnitResponseDto(getDataByUnitList);
    }

    public List<GetDataByUnitResponseDto> getDataByUnit(UUID unitId) {
        long now = System.currentTimeMillis();
        long start = now - (5L * 24 * 60 * 60 * 1000);
        List<GetDataByUnit> getDataByUnitList = dataRepository.getDataByUnit(unitId, start, now);
        return toGetDataByUnitResponseDto(getDataByUnitList);
    }

    private List<GetDataByUnitResponseDto> toGetDataByUnitResponseDto(List<GetDataByUnit> getDataByUnitList) {
        List<GetDataByUnitResponseDto> convertedList = new ArrayList<>();

        for (GetDataByUnit item : getDataByUnitList) {
            GetDataByUnitResponseDto converted = new GetDataByUnitResponseDto(
                    formatDate.formatToDate(item.getDateTime(), true),
                    String.valueOf(item.getRefMin()),
                    String.valueOf(item.getRefCur()),
                    String.valueOf(item.getRefMax()),
                    String.valueOf(item.getEnvMin()),
                    String.valueOf(item.getEnvCur()),
                    String.valueOf(item.getEnvMax()),
                    String.valueOf(item.getUserName())
            );
            convertedList.add(converted);
        }

        return convertedList;
    }
}