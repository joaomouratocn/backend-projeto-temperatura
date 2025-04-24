package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<RegisterResponseDto> saveUser(
            @RequestBody @Validated RegisterDto registerDto) {
        RegisterResponseDto userResponseDto = userService.updateUser(registerDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
