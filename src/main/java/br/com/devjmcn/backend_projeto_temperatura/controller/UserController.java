package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.ErrorResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.SuccessResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.SecurityConfig;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassDto;
import br.com.devjmcn.backend_projeto_temperatura.service.UserService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Usuários", description = "Controlador para usuários")
public class UserController {
    @Autowired
    UserService userService;

    @PatchMapping("/updatepass")
    @Operation(summary = "Alterar senha", description = "Metodo para alteração de senha de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao alterar senha",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<SuccessResponse> updatePass(@RequestBody @Validated NewPassDto newPassDto) {
        SuccessResponse response = userService.updatePass(newPassDto);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Metodo para registrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao realizar registro",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<SuccessResponse> register(@RequestBody @Validated RegisterDto registerDto) {
        SuccessResponse registerResponseDto = userService.register(registerDto);
        return ResponseEntity.ok(registerResponseDto);
    }
}
