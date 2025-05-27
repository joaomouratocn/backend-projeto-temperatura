package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.ErrorResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.SuccessResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.SecurityConfig;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataByUnitResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.GetDataIntervalDto;
import br.com.devjmcn.backend_projeto_temperatura.model.data.dtos.SaveDataDto;
import br.com.devjmcn.backend_projeto_temperatura.service.DataService;
import br.com.devjmcn.backend_projeto_temperatura.util.FormatDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/data")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Dados", description = "Controlador para inserção e requisição dos dados")
public class DataController {

    @Autowired
    DataService dataService;

    @Autowired
    FormatDate formatDate;

    @PostMapping
    @Operation(summary = "Salvar dados", description = "Metodo inserção dos dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados salvo com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error na inserção do dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<SuccessResponse> saveData(@RequestBody @Validated SaveDataDto saveDataDto) {
        SuccessResponse saveResponseDto = dataService.saveData(saveDataDto);
        return ResponseEntity.ok(saveResponseDto);
    }


    @GetMapping("{uuid}")
    @Operation(summary = "Pegar dados pela unidade", description = "Metodo que retorna os dados de uma determinada unidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados carregados com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao garregar dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<List<GetDataByUnitResponseDto>> getData(@PathVariable UUID uuid) {
        List<GetDataByUnitResponseDto> getDataByUnitList = dataService.getDataByUnit(uuid);
        return ResponseEntity.ok(getDataByUnitList);
    }

    @GetMapping("/interval")
    @Operation(summary = "Pegar dados de um intervalo", description = "Metodo para pegar os dados em um intervalo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados carregados com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao carregar dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
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
