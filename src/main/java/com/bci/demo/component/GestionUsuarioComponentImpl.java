package com.bci.demo.component;

import com.bci.demo.model.UsuarioModel;
import com.bci.demo.repository.GestionUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GestionUsuarioComponentImpl implements GestionUsuarioComponent {

    @Autowired
    private GestionUsuarioRepository gestionUsuarioRepository;

    @Override
    public UsuarioModel registrarUsuario(UsuarioModel usuarioModel){
        return gestionUsuarioRepository.saveAndFlush(usuarioModel);
    }

    @Override
    public List<UsuarioModel> obtenerUsuarios(){
        return gestionUsuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioModel> obtenerUsuario(String username){
        return gestionUsuarioRepository.findByUsername(username);
    }
}
