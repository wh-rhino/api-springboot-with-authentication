package com.bci.demo.controller;

import com.bci.demo.dto.DatosConsultaUsuarioDTO;
import com.bci.demo.dto.DatosUsuarioDTO;
import com.bci.demo.service.GestionUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GestionUsuarioController {

    private final GestionUsuarioService gestionUsuarioService;

    @PostMapping(value = "/users/register-user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearUsuario(){
        final DatosUsuarioDTO body = gestionUsuarioService.registrarDatosUsuario();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, String.valueOf(body))
                .body(body);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/users/get-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUsuarios(){
        final List<DatosConsultaUsuarioDTO> body = gestionUsuarioService.obtenerUsuarios();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, String.valueOf(body))
                .body(body);
    }

}
