package com.bci.demo.component;

import com.bci.demo.model.UsuarioModel;
import com.bci.demo.repository.GestionUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GestionUsuarioComponent {

    private final GestionUsuarioRepository gestionUsuarioRepository;

    public UsuarioModel registrarUsuario(UsuarioModel usuarioModel){
        return gestionUsuarioRepository.saveAndFlush(usuarioModel);
    }

    public List<UsuarioModel> obtenerUsuarios(){
        return gestionUsuarioRepository.findAll();
    }

    public Optional<UsuarioModel> obtenerUsuario(String username){
        return gestionUsuarioRepository.findByUsername(username);
    }
}
