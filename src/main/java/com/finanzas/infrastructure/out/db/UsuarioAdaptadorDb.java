package com.finanzas.infrastructure.out.db;

import com.finanzas.application.port.out.UsuarioPuertoSalida;
import com.finanzas.domain.model.Usuario;
import com.finanzas.infrastructure.mapper.UsuarioMapper;
import com.finanzas.infrastructure.out.db.repository.UsuarioRepositorioJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAdaptadorDb implements UsuarioPuertoSalida {

    private final UsuarioRepositorioJpa usuarioRepositorioJpa;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardar(Usuario usuario) {
        var entidad = usuarioMapper.aEntidadJpa(usuario);
        return usuarioMapper.aDominio(usuarioRepositorioJpa.save(entidad));
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepositorioJpa.findByCorreo(correo).map(usuarioMapper::aDominio);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepositorioJpa.findById(id).map(usuarioMapper::aDominio);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return usuarioRepositorioJpa.existsByCorreo(correo);
    }
}
