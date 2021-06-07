package com.bci.demo.service;

import com.bci.demo.dto.DatosConsultaUsuarioDTO;
import com.bci.demo.dto.DatosUsuarioDTO;

import java.util.List;

public interface GestionUsuarioService {
    DatosUsuarioDTO registrarDatosUsuario();

    List<DatosConsultaUsuarioDTO> obtenerUsuarios();
}
