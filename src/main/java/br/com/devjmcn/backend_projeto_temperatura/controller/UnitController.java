package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitservice;

    @GetMapping
    public ResponseEntity<List<UnitDto>> getUnits() {
        List<UnitDto> unitDtoList = unitservice.getAllUnits();
        return ResponseEntity.ok(unitDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnitName(@PathVariable @Validated UUID id) {
        UnitDto unitDto = unitservice.getUnit(id);
        return ResponseEntity.ok(unitDto);
    }

    @GetMapping("/byuser")
    public ResponseEntity<UnitDto> getUnitByUser() {
        UnitDto unitDto = unitservice.getUnitByUser();
        return ResponseEntity.ok(unitDto);
    }
}
