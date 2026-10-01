package com.finanzas.infrastructure.out.db.repository;

import com.finanzas.infrastructure.out.db.entity.UsuarioEntidadJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorioJpa extends JpaRepository<UsuarioEntidadJpa, Long> {

    Optional<UsuarioEntidadJpa> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
