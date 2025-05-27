package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.ErrorResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.SecurityConfig;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.unit.UnitDto;
import br.com.devjmcn.backend_projeto_temperatura.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Unidades", description = "Controlador de unidade")
public class UnitController {

    @Autowired
    private UnitService unitservice;

    @GetMapping
    @Operation(summary = "Carregar todas unidades", description = "Metodo que retorna uma lista com todas unidades cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidades carregadas com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao carregar unidade",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<List<UnitDto>> getUnits() {
        List<UnitDto> unitDtoList = unitservice.getAllUnits();
        return ResponseEntity.ok(unitDtoList);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Pegar unidade por ID", description = "Metodo que retorna a unidade pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade localizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unidade não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<UnitDto> getUnitName(@PathVariable @Validated UUID id) {
        UnitDto unitDto = unitservice.getUnit(id);
        return ResponseEntity.ok(unitDto);
    }


    @GetMapping("/byuser")
    @Operation(summary = "Pegar unidade por usuário", description = "Metodo que retorna unidade do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade localizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unidade não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<UnitDto> getUnitByUser() {
        UnitDto unitDto = unitservice.getUnitByUser();
        return ResponseEntity.ok(unitDto);
    }
}
