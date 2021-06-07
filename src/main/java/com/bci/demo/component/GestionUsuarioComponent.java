package com.bci.demo.component;

import com.bci.demo.model.UsuarioModel;
import java.util.List;
import java.util.Optional;

public interface GestionUsuarioComponent {
    UsuarioModel registrarUsuario(UsuarioModel usuarioModel);

    List<UsuarioModel> obtenerUsuarios();

    Optional<UsuarioModel> obtenerUsuario(String username);
}

