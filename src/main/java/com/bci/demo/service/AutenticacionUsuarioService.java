package com.bci.demo.service;

import com.bci.demo.component.AutenticacionUsuarioComponent;
import com.bci.demo.constant.Constant;
import com.bci.demo.dto.AutenticacionUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class AutenticacionUsuarioService {

    private final HttpServletRequest httpServletRequest;
    private final AutenticacionUsuarioComponent autenticacionUsuarioComponent;

    public AutenticacionUsuarioDTO login() {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        if (null == username || null == password) {
            throw new ValidationException(Constant.CODE_ERROR_USUARIO_REGISTRADO);
        }

        try {
            return autenticacionUsuarioComponent.login(username, password);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Constant.CODE_ERROR_USUARIO_AUTENTICACION);
        }
    }
}
