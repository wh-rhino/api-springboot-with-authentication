package com.bci.demo.component;

import com.bci.demo.dto.AutenticacionUsuarioDTO;
import com.bci.demo.model.UsuarioModel;
import com.bci.demo.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutenticacionUsuarioComponent {

    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;
    
    public AutenticacionUsuarioDTO login(String username, String password){
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UsuarioModel usuarioModel = (UsuarioModel) authentication.getPrincipal();

        return crearAutenticacionUsuarioDTO(usuarioModel);
    }

    private AutenticacionUsuarioDTO crearAutenticacionUsuarioDTO(UsuarioModel usuarioModel){
        return AutenticacionUsuarioDTO.builder()
                .id(UUID.randomUUID())
                .isEnabled(usuarioModel.isEnabled())
                .token(jwtConfig.generarToken(usuarioModel.getUsername()))
                .build();
    }
}
