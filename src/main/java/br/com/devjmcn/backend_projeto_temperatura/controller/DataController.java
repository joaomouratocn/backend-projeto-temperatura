package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.service.DataService;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Autowired
    FormatDate formatDate;

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
    public ResponseEntity<List<GetDataByUnitResponseDto>> getDataInterval(
            @RequestParam @NotNull UUID unitid,
            @RequestParam @NotBlank String startdate,
            @RequestParam @NotBlank String enddate
    ) {
        long convertedDataStart = formatDate.formatToMilli(startdate, false);
        long convertedDateEnd = formatDate.formatToMilli(enddate, true);

        System.out.println(convertedDataStart);

        GetDataIntervalDto getDataIntervalDto =
                new GetDataIntervalDto(unitid, convertedDataStart, convertedDateEnd);

        List<GetDataByUnitResponseDto> getDataByUnitList =
                dataService.getDataByUnit(getDataIntervalDto);
        return ResponseEntity.ok(getDataByUnitList);
    }
}
