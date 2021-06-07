package com.bci.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosConsultaUsuarioDTO implements Serializable {

    private static final long serialVersionUID = -4139862657968447700L;

    private String username;
    private String email;
    private String created;
    private String modified;
    private String last_login;
    private String active;
    private UUID identificador;

}
