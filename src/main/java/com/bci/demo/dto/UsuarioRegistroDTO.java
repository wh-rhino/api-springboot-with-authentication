package com.bci.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UsuarioRegistroDTO implements Serializable {

    private static final long serialVersionUID = -1229639235072331880L;

    private String name;
    private String email;
    private String password;
    private List<Phones> phones;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Phones{
        private String number;
        private String cityCode;
        private String countryCode;
    }
}
