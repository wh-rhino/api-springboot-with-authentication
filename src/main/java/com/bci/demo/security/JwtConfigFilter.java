package com.bci.demo.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.bci.demo.constant.Constant;
import com.bci.demo.repository.GestionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class JwtConfigFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private GestionUsuarioRepository gestionUsuarioRepository;

    private static final String PREFIX_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(isToken(header)) {

                final boolean starts = header.startsWith(PREFIX_BEARER);
                if (header.isEmpty() || !starts) {
                    filterChain.doFilter(request, response);
                    return;
                }

                final String token = header.split(" ")[1].trim();
                final String path = request.getServletPath();
                final String username = request.getParameter("username");

                if(path.equalsIgnoreCase("/users/register-user")){
                    if (!jwtConfig.validarTokenRegistroUsuario(token, username)) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }

                Claim claim = null;

                if (path.equalsIgnoreCase("/users/login") || path.equalsIgnoreCase("/users/get-users")) {
                    claim = jwtConfig.validarTokenAutenticacionConsultaUsuario(token, username);
                }

                if (claim.isNull()) {
                    SecurityContextHolder.clearContext();
                } else {
                    final List<String> grant = claim.asList(String.class);

                    UserDetails userDetails = gestionUsuarioRepository
                            .findByUsername(username)
                            .orElse(null);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,
                            grant != null ? grant.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()) : ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(null)
                    );

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }

            filterChain.doFilter(request, response);
        } catch (IOException | ServletException | TokenExpiredException | IllegalArgumentException | JWTCreationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            throw new ServletException(Constant.CODE_ERROR_USUARIO_AUTENTICACION); // TODO: Handle Exception?
        }
    }


    private boolean isToken(String header){
        if (null == header || !header.startsWith(PREFIX_BEARER)) {
            return false;
        }
        return true;
    }

}
