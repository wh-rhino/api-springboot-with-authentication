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
public class AutenticacionUsuarioDTO implements Serializable {

    private static final long serialVersionUID = -2330475671691959424L;

    private UUID id;
    private String token;
    private boolean isEnabled;

}
