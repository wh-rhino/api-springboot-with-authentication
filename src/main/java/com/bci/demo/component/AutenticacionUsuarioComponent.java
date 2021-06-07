package com.bci.demo.component;

import com.bci.demo.dto.AutenticacionUsuarioDTO;

public interface AutenticacionUsuarioComponent {
    AutenticacionUsuarioDTO login(String username, String password);
}
