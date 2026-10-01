package com.finanzas.infrastructure.out.db.repository;

import com.finanzas.infrastructure.out.db.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
