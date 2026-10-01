package com.finanzas.application.port.out;

import com.finanzas.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioPuertoSalida {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCorreo(String correo);

    Optional<Usuario> buscarPorId(Long id);

    boolean existePorCorreo(String correo);
}
