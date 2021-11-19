package com.bci.demo.controller;

import com.bci.demo.dto.AutenticacionUsuarioDTO;
import com.bci.demo.service.AutenticacionUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RolesAllowed("ROLE_ADMIN")
public class AutenticacionUsuarioController {

    private final AutenticacionUsuarioService autenticacionUsuarioService;

    @PostMapping(value = "/users/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(){
        final AutenticacionUsuarioDTO login = autenticacionUsuarioService.login();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, String.valueOf(login))
                .body(login);
    }
}
