package com.bci.demo


import com.bci.demo.component.AutenticacionUsuarioComponent
import com.bci.demo.constant.Constant
import com.bci.demo.dto.AutenticacionUsuarioDTO

import com.bci.demo.service.AutenticacionUsuarioService
import spock.lang.Specification
import spock.lang.Unroll
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException

class AutenticacionUsuarioServiceSpecs extends Specification {

    private AutenticacionUsuarioComponent autenticacionUsuarioComponent
    private AutenticacionUsuarioService autenticacionUsuarioService
    private HttpServletRequest httpServletRequest

    def setup(){
        autenticacionUsuarioComponent = Mock(AutenticacionUsuarioComponent)
        httpServletRequest = Stub()
        autenticacionUsuarioService = new AutenticacionUsuarioService(httpServletRequest, autenticacionUsuarioComponent)
    }

    @Unroll
    def "Autenticacion de un nuevo usuario" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("password") >> "Aythi34"
        httpServletRequest.getParameter("username") >> "Juan Rodriguez"

        autenticacionUsuarioComponent.login(_,_) >> login

        when: "Se autentica"

        def response = autenticacionUsuarioService.login()

        then: "El resultado esperado es el siguiente"

        response.enabled == expected.enabled
        response.token == expected.token

        where: "La respuesta del proceso de autenticacion es exitoso"

        login   | expected
        autenticacionUsuarioDTO() | autenticacionUsuarioDTO()
    }

    @Unroll
    def "Autenticacion de un nuevo usuario error parametros de entrada" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("password") >> null
        httpServletRequest.getParameter("username") >> null

        when: "Se autentica"

        autenticacionUsuarioService.login()

        then: "El resultado esperado es el siguiente"

        thrown(ValidationException)
    }

    @Unroll
    def "Autenticacion de un nuevo usuario problemas de autenticacion" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("password") >> "Aythi34"
        httpServletRequest.getParameter("username") >> "Juan Rodriguez"

        autenticacionUsuarioComponent.login(_,_) >> { throw new IllegalArgumentException(Constant.CODE_ERROR_USUARIO_AUTENTICACION) }

        when: "Se autentica"

        autenticacionUsuarioService.login()

        then: "El resultado esperado es el siguiente"

        thrown(IllegalArgumentException)
    }

    private AutenticacionUsuarioDTO autenticacionUsuarioDTO(){
        return AutenticacionUsuarioDTO.builder()
                .id(UUID.randomUUID())
                .isEnabled(true)
                .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoMCJ9.QDYxAPWfzpz7s0TZVi2s5GUXdFn3klCrAFPZYUhLw11A9fG8Jt9VBHhhOu47f-eaPuf-7njrXPKQs1t0cD223g")
                .build();
    }

}
