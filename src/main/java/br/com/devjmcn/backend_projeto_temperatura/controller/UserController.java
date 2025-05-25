package br.com.devjmcn.backend_projeto_temperatura.controller;

import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassDto;
import br.com.devjmcn.backend_projeto_temperatura.model.dtos.user.NewPassResponseSuccess;
import br.com.devjmcn.backend_projeto_temperatura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PatchMapping("/updatepass")
    public ResponseEntity<NewPassResponseSuccess> updatePass(@RequestBody @Validated NewPassDto newPassDto) {
        NewPassResponseSuccess response = userService.updatePass(newPassDto);

        return ResponseEntity.ok(response);
    }
}
