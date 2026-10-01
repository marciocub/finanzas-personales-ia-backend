package com.finanzas.infrastructure.out.db;

import com.finanzas.application.port.out.UsuarioOutPort;
import com.finanzas.domain.model.Usuario;
import com.finanzas.infrastructure.mapper.UsuarioMapper;
import com.finanzas.infrastructure.out.db.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioDbAdapter implements UsuarioOutPort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardar(Usuario usuario) {
        var entidad = usuarioMapper.aEntidadJpa(usuario);
        return usuarioMapper.aDominio(usuarioRepository.save(entidad));
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).map(usuarioMapper::aDominio);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::aDominio);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }
}
