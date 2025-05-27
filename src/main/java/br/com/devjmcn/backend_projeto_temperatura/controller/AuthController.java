package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.ErrorResponse;
import br.com.devjmcn.backend_projeto_temperatura.infra.security.SecurityConfig;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.AuthResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.service.AuthService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Autenticação", description = "Controlador para autenticar usuários")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Logar usuário", description = "Metodo para logar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário inexistente ou senha inválida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Validated AuthDto authDto) {
        AuthResponseDto authResponseDto = authService.auth(authDto);
        return ResponseEntity.ok(authResponseDto);
    }
}
