package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.GetDataByUnitResponse;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.data.SaveResponseDto;
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

    @GetMapping("/{unitId}")
    public ResponseEntity<List<GetDataByUnitResponse>> getData(@PathVariable @Validated UUID unitId) {
        List<GetDataByUnitResponse> getDataByUnitResponseList = dataService.getDataByUnit(unitId);
        return ResponseEntity.ok(getDataByUnitResponseList);
    }
}
