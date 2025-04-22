package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.AuthUserDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.AuthUserResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.SaveUserDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.SaveUserResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SaveUserResponseDto> saveUser(
            @RequestBody @Validated SaveUserDto saveUserDto) {
        SaveUserResponseDto userResponseDto = userService.save(saveUserDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthUserResponseDto> authUser(@RequestBody @Validated AuthUserDto authUserDto){
        AuthUserResponseDto authUserResponseDto = userService.auth(authUserDto);
        return ResponseEntity.ok(authUserResponseDto);
    }
}
