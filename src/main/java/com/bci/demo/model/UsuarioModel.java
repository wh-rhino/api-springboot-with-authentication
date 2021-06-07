package com.bci.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario"}))
public class UsuarioModel implements UserDetails, Serializable {

    @Id
    @SequenceGenerator(name = "id_usuario_seq", sequenceName = "id_usuario_seq", allocationSize = 1)
    @GeneratedValue
    @Column(name = "id_usuario", unique = true, updatable = false)
    private UUID id;

    @Column(name = "nombre", updatable = false, nullable = false, length = 30)
    private String username;

    @Column(name = "correo", updatable = false, nullable = false, length = 30)
    private String mail;

    @Column(name = "password", updatable = false, nullable = false, length = 2000)
    private String password;

    @Column(name = "token", updatable = false, nullable = false, length = 2000)
    private String token;

    @Column(name = "activo", updatable = false, nullable = false, length = 1)
    private String active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", nullable = false)
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimo_acceso", nullable = false)
    private Date last_login;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
