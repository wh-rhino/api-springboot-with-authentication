package com.bci.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bci.demo.constant.Constant;
import com.bci.demo.repository.GestionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private GestionUsuarioRepository gestionUsuarioRepository;

    public String generarToken(String userName){
        try {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final Date date = new Date();
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(userName)
                .withIssuedAt(date)
                .withExpiresAt(new Date(System.currentTimeMillis() + 200000))
                .withClaim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new IllegalArgumentException(Constant.CODE_ERROR_USUARIO_AUTENTICACION);
        }
    }

    public boolean validarTokenRegistroUsuario(String token, String username){
        try {
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            final String sign = generarToken(username);
            DecodedJWT jwt = verificarToken(username, algorithm, sign);
            return token.equalsIgnoreCase(jwt.getToken());
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException(Constant.CODE_ERROR_USUARIO_TOKEN_EXP);
        }
    }

    private DecodedJWT verificarToken(String username, Algorithm algorithm, String sign) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .withSubject(username)
                .build();
        return verifier.verify(sign);
    }

    public Claim validarTokenAutenticacionConsultaUsuario(String token, String username){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = verificarToken(username, algorithm, token);
            return jwt.getClaims().get("authorities");
        } catch (TokenExpiredException e){
            throw new TokenExpiredException(Constant.CODE_ERROR_USUARIO_TOKEN_EXP);
        }
    }
}
