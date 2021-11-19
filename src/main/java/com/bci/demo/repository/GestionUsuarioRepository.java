package com.bci.demo.repository;

import com.bci.demo.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface GestionUsuarioRepository extends JpaRepository<UsuarioModel, String> {

    @Override
    <S extends UsuarioModel> S saveAndFlush(S entity);

    Optional<UsuarioModel> findByUsername(String id);

    @Override
    List<UsuarioModel> findAll();
}
