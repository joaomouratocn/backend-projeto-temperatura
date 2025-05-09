package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.authentication.RegisterResponseDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassResponseSuccess;
import br.com.devjmcn.backend_projeto_temperatura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PatchMapping("/api/updatepass")
    public ResponseEntity<NewPassResponseSuccess> updatePass(@RequestBody @Validated NewPassDto newPassDto){
        NewPassResponseSuccess response = userService.updatePass(newPassDto);

        return ResponseEntity.ok(response);
    }
}
