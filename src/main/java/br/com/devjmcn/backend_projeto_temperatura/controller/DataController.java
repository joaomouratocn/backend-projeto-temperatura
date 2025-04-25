package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.data.GetDataByUnit;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    DataService dataService;

    @PostMapping
    public ResponseEntity<SaveResponseDto> saveData(@RequestBody @Validated SaveDataDto saveDataDto) {
        SaveResponseDto saveResponseDto = dataService.saveData(saveDataDto);
        return ResponseEntity.ok(saveResponseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<GetDataByUnitResponseDto>> getData(@PathVariable UUID id) {
        List<GetDataByUnitResponseDto> getDataByUnitList = dataService.getDataByUnit(id);
        return ResponseEntity.ok(getDataByUnitList);
    }

    @GetMapping("/interval")
    public ResponseEntity<List<GetDataByUnitResponseDto>> getDataInterval(@RequestBody @Validated GetDataIntervalDto getDataIntervalDto){
        List<GetDataByUnitResponseDto> getDataByUnitList =
                dataService.getDataByUnit(getDataIntervalDto.unitId(), getDataIntervalDto.start(), getDataIntervalDto.end());
        return ResponseEntity.ok(getDataByUnitList);
    }
}
