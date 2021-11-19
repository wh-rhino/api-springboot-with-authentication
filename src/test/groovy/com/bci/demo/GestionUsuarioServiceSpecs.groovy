package com.bci.demo


import com.bci.demo.component.GestionUsuarioComponent
import com.bci.demo.model.UsuarioModel
import com.bci.demo.security.JwtConfig

import com.bci.demo.service.GestionUsuarioService
import com.bci.demo.util.Utils
import spock.lang.Specification
import spock.lang.Unroll
import javax.servlet.http.HttpServletRequest
import javax.validation.ValidationException

class GestionUsuarioServiceSpecs extends Specification {

    private GestionUsuarioComponent gestionUsuarioComponent
    private JwtConfig jwtConfig
    private GestionUsuarioService gestionUsuarioService
    private HttpServletRequest httpServletRequest
    private UsuarioModel usuarioModel

    def setup(){
        gestionUsuarioComponent = Mock(GestionUsuarioComponent)
        jwtConfig = Mock(JwtConfig)
        httpServletRequest = Stub()
        usuarioModel = Mock()
        gestionUsuarioService = new GestionUsuarioService(gestionUsuarioComponent, jwtConfig, httpServletRequest)
    }

    @Unroll
    def "Registrar un nuevo usuario" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("email") >> "demo@gmail.com"
        httpServletRequest.getParameter("password") >> "Aythi34"
        httpServletRequest.getParameter("username") >> "Juan Rodriguez"

        gestionUsuarioComponent.obtenerUsuarios() >> usuarios
        gestionUsuarioComponent.obtenerUsuario(_) >> Optional.empty()
        gestionUsuarioComponent.registrarUsuario(_) >> model

        when: "Se registra"

        def response = gestionUsuarioService.registrarDatosUsuario()

        then: "El resultado esperado es el siguiente"

        response.active == expected.active
        response.created == Utils.format(expected.created)
        response.last_login == Utils.format(expected.last_login)
        response.token == expected.token

        where: "La respuesta del proceso de registro es exitosa"

        model   | usuarios  | expected
        obtenerUsuario() | obtenerUsuarios() | obtenerUsuario()
    }

    @Unroll
    def "Registrar un nuevo usuario error en parametros de entrada" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("email") >> null
        httpServletRequest.getParameter("password") >> null
        httpServletRequest.getParameter("username") >> null

        gestionUsuarioComponent.obtenerUsuarios() >> usuarios
        gestionUsuarioComponent.obtenerUsuario(_) >> Optional.empty()
        gestionUsuarioComponent.registrarUsuario(_) >> model

        when: "Se registra"

        gestionUsuarioService.registrarDatosUsuario()

        then: "El resultado esperado es una excepcion"

        thrown(ValidationException)

        where: "La respuesta del proceso de registro es exitosa"

        model   | usuarios  | expected
        obtenerUsuario() | obtenerUsuarios() | obtenerUsuario()
    }

    @Unroll
    def "Registrar un nuevo usuario error en formato de parametros de entrada" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("email") >> "demo"
        httpServletRequest.getParameter("password") >> "12345"
        httpServletRequest.getParameter("username") >> "Juan Rodriguez"

        gestionUsuarioComponent.obtenerUsuarios() >> usuarios
        gestionUsuarioComponent.obtenerUsuario(_) >> Optional.empty()
        gestionUsuarioComponent.registrarUsuario(_) >> model

        when: "Se registra"

        gestionUsuarioService.registrarDatosUsuario()

        then: "El resultado esperado es el siguiente"

        thrown(ValidationException)

        where: "La respuesta del proceso de registro es exitosa"

        model   | usuarios  | expected
        obtenerUsuario() | obtenerUsuarios() | obtenerUsuario()
    }

    @Unroll
    def "Registrar un nuevo usuario donde el correo ya existe" () {
        given: "Un nuevo usuario"

        httpServletRequest.getParameter("email") >> "demo@gmail.com"
        httpServletRequest.getParameter("password") >> "Aythi34"
        httpServletRequest.getParameter("username") >> "Juan Rodriguez"

        gestionUsuarioComponent.obtenerUsuarios() >> usuarios
        gestionUsuarioComponent.obtenerUsuario(_) >> Optional.of(usuarioModel)
        gestionUsuarioComponent.registrarUsuario(_) >> model

        when: "Se registra"

        gestionUsuarioService.registrarDatosUsuario()

        then: "El resultado esperado es el siguiente"

        thrown(ValidationException)

        where: "La respuesta del proceso de registro es exitosa"

        model   | usuarios  | expected
        obtenerUsuario() | obtenerUsuarios() | obtenerUsuario()
    }

    @Unroll
    def "Obtener usuarios registrados" () {
        given: "Un nuevo usuario registrado"

        gestionUsuarioComponent.obtenerUsuarios() >> usuarios

        when: "Se registra"

        def response = gestionUsuarioService.obtenerUsuarios()

        then: "El resultado esperado es el siguiente"

        response.get(0).last_login == Utils.format(expected.get(0).last_login)
        response.get(0).created == Utils.format(expected.get(0).created)
        response.get(0).active == expected.get(0).active
        response.get(0).modified == Utils.format(expected.get(0).modified)

        where: "La respuesta del proceso de consulta es exitosa"

        usuarios  | expected
        obtenerUsuarios() | obtenerUsuarios()
    }

    @Unroll
    def "Obtener usuarios, no se encuentras usuarios registrados" () {
        given: "Un nuevo usuario registrado"

        gestionUsuarioComponent.obtenerUsuarios() >> new ArrayList<UsuarioModel>()

        when: "Se registra"

        gestionUsuarioService.obtenerUsuarios()

        then: "El resultado esperado es el siguiente"

        thrown(ValidationException)
    }

    private UsuarioModel obtenerUsuario() {
        final def date = new Date()
        String username = "Juan Rodriguez"
        return UsuarioModel.builder()
                .active("0")
                .created(date)
                .id(UUID.randomUUID())
                .last_login(new Date())
                .mail("demo@gmail.com")
                .modified(date)
                .password("Aythi34")
                .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoMCJ9.QDYxAPWfzpz7s0TZVi2s5GUXdFn3klCrAFPZYUhLw11A9fG8Jt9VBHhhOu47f-eaPuf-7njrXPKQs1t0cD223g")
                .username(username)
                .build()
    }

    private List<UsuarioModel> obtenerUsuarios() {
        def usuarioModel = obtenerUsuario()
        List<UsuarioModel> usuarioModels = new ArrayList<>()
        usuarioModels << usuarioModel
        return usuarioModels
    }

}
