package com.bci.demo.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bci.demo.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.IOException;

@ControllerAdvice
public class ExcepcionConfig {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Mensaje> validationValidationException(HttpServletRequest request, ValidationException validationException){
        final Mensaje mensaje = determinarMensaje(validationException);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Mensaje(mensaje.codigo, mensaje.mensaje));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Mensaje> validationTokenExpiredException(HttpServletRequest request, TokenExpiredException tokenExpiredException){
        final Mensaje mensaje = determinarMensaje(tokenExpiredException);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new Mensaje(mensaje.codigo, mensaje.mensaje));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Mensaje> validationExceptionIllegalArgumentException(HttpServletRequest request, IllegalArgumentException illegalArgumentException){
        final Mensaje mensaje = determinarMensaje(illegalArgumentException);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new Mensaje(mensaje.codigo, mensaje.mensaje));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Mensaje> validationExceptionServletException(HttpServletRequest request, ServletException servletException){
        final Mensaje mensaje = determinarMensaje(servletException);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new Mensaje(mensaje.codigo, mensaje.mensaje));
    }

    private Mensaje determinarMensaje (Object object) {
        final Mensaje mensaje = Mensaje.builder().build();
        String codigo;
        if (object instanceof ValidationException) {
            ValidationException validationException = (ValidationException) object;
            codigo = validationException.getMessage();
            extraerMensaje(mensaje, codigo);
            // TODO: Handle exception?
        } else if (object instanceof TokenExpiredException) {
            TokenExpiredException tokenExpiredException = (TokenExpiredException) object;
            codigo = tokenExpiredException.getMessage();
            extraerMensaje(mensaje, codigo, Constant.CODE_MSJ_USUARIO_TOKEN_EXP);
        } else if (object instanceof IllegalArgumentException) {
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) object;
            codigo = illegalArgumentException.getMessage();
            extraerMensaje(mensaje, codigo, Constant.CODE_MSJ_USUARIO_AUTENTICACION);
        }else if (object instanceof IOException) {
            IOException ioException = (IOException) object;
            codigo = ioException.getMessage();
            extraerMensaje(mensaje, codigo, Constant.CODE_MSJ_USUARIO_AUTENTICACION);
        } else if (object instanceof ServletException) {
            ServletException servletException = (ServletException) object;
            codigo = servletException.getMessage();
            extraerMensaje(mensaje, codigo, Constant.CODE_MSJ_USUARIO_AUTENTICACION);
        }
        return mensaje;
    }

    private void extraerMensaje(Mensaje mensaje, String codigo) {
        if (codigo.equalsIgnoreCase(Constant.CODE_ERROR_PARAMETROS_ENTRADA)) {
            mensaje.setCodigo(codigo);
            mensaje.setMensaje(Constant.CODE_MSJ_PARAMETROS_ENTRADA);
        } else if (codigo.equalsIgnoreCase(Constant.CODE_ERROR_USUARIO_REGISTRADO)) {
            mensaje.setCodigo(codigo);
            mensaje.setMensaje(Constant.CODE_MSJ_USUARIO_REGISTRADO);
        } else if (codigo.equalsIgnoreCase(Constant.CODE_ERROR_USUARIOS_NO_REGISTRADOS)) {
            mensaje.setCodigo(codigo);
            mensaje.setMensaje(Constant.CODE_MSJ_USUARIOS_NO_REGISTRADOS);
        } else if (codigo.equalsIgnoreCase(Constant.CODE_ERROR_USUARIO_FORMATO)) {
            mensaje.setCodigo(codigo);
            mensaje.setMensaje(Constant.CODE_MSJ_USUARIO_FORMATO);
        }
    }

    private void extraerMensaje(Mensaje mensaje, String codigo, String text) {
        if (codigo.equalsIgnoreCase(codigo)) {
            mensaje.setCodigo(codigo);
            mensaje.setMensaje(text);
        }
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Mensaje {
        private String codigo;
        private String mensaje;
    }

}
