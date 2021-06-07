package com.bci.demo.service;

import com.bci.demo.component.GestionUsuarioComponent;
import com.bci.demo.constant.Constant;
import com.bci.demo.dto.DatosConsultaUsuarioDTO;
import com.bci.demo.dto.DatosUsuarioDTO;
import com.bci.demo.dto.UsuarioRegistroDTO;
import com.bci.demo.model.UsuarioModel;
import com.bci.demo.security.JwtConfig;
import com.bci.demo.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GestionUsuarioServiceImpl implements GestionUsuarioService {

    private final GestionUsuarioComponent gestionUsuarioComponent;

    private final JwtConfig jwtConfig;

    private final HttpServletRequest httpServletRequest;

    @Override
    public DatosUsuarioDTO registrarDatosUsuario(){

        final String username = httpServletRequest.getParameter("username");
        final String password = httpServletRequest.getParameter("password");
        final String email = httpServletRequest.getParameter("email");

        if (null == username || null == password || null == email) {
            throw new ValidationException(Constant.CODE_ERROR_PARAMETROS_ENTRADA);
        }

        if ( (!Utils.format(email, "^[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+$")) || (!Utils.format(password, "^[A-Z]{1}[a-z]+[0-9]{2}$")) ) {
            throw new ValidationException(Constant.CODE_ERROR_USUARIO_FORMATO);
        }

        final List<UsuarioModel> usuarioModels = gestionUsuarioComponent.obtenerUsuarios();

        if (!usuarioModels.isEmpty()) {
            if (gestionUsuarioComponent.obtenerUsuario(username).isPresent()) {
                throw new ValidationException(Constant.CODE_ERROR_USUARIO_REGISTRADO);
            }
        }
        UsuarioModel usuario = registrarUsuario(username, password, email);

        return completarRegistroUsuario(usuario);
    }

    private UsuarioModel registrarUsuario(String username, String password, String email) {
        UsuarioModel usuarioModel;
        UsuarioModel usuario;
        usuarioModel = iniciarRegistroUsuario(username, password, email);
        usuario = gestionUsuarioComponent.registrarUsuario(usuarioModel);
        return usuario;
    }

    private UsuarioModel iniciarRegistroUsuario(String username, String password, String email){

        UsuarioRegistroDTO usuarioRegistroDTO = UsuarioRegistroDTO.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();

        Date date = new Date();

        final String name = usuarioRegistroDTO.getName();
        final String pwd = usuarioRegistroDTO.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        final String encode = passwordEncoder.encode(pwd);
        final String mail = usuarioRegistroDTO.getEmail();

        return UsuarioModel.builder()
                .id(UUID.randomUUID())
                .username(name)
                .mail(mail)
                .password(encode)
                .token(jwtConfig.generarToken(name))
                .active("0")
                .created(date)
                .modified(date)
                .last_login(date)
                .build();
    }

    private DatosUsuarioDTO completarRegistroUsuario(UsuarioModel usuarioModel){
        return DatosUsuarioDTO.builder()
                .identificador(usuarioModel.getId())
                .created(Utils.format(usuarioModel.getCreated()))
                .modified(Utils.format(usuarioModel.getModified()))
                .last_login(Utils.format(usuarioModel.getLast_login()))
                .token(usuarioModel.getToken())
                .active(usuarioModel.getActive()) // TODO: Active?
                .build();
    }

    @Override
    public List<DatosConsultaUsuarioDTO> obtenerUsuarios(){
        final List<UsuarioModel> usuarioModels = gestionUsuarioComponent.obtenerUsuarios();
        List<DatosConsultaUsuarioDTO> datosConsultaUsuarioDTOList = new ArrayList<>();
        if (usuarioModels.isEmpty()) {
            throw new ValidationException(Constant.CODE_ERROR_USUARIOS_NO_REGISTRADOS);
        }
        usuarioModels.stream().forEach(usuarioModel -> {
            DatosConsultaUsuarioDTO datosConsultaUsuarioDTO = DatosConsultaUsuarioDTO.builder()
                    .active(usuarioModel.getActive())
                    .created(Utils.format(usuarioModel.getCreated()))
                    .email(usuarioModel.getMail())
                    .identificador(usuarioModel.getId())
                    .last_login(Utils.format(usuarioModel.getLast_login()))
                    .modified(Utils.format(usuarioModel.getModified()))
                    .username(usuarioModel.getUsername())
                    .build();
            datosConsultaUsuarioDTOList.add(datosConsultaUsuarioDTO);
        });
        return datosConsultaUsuarioDTOList;
    }
}
