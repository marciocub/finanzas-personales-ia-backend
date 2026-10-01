package com.finanzas.application.service;

import com.finanzas.application.dto.ComandoInicioSesion;
import com.finanzas.application.dto.ComandoRegistro;
import com.finanzas.application.dto.ResultadoAutenticacion;
import com.finanzas.application.port.in.AutenticacionUseCase;
import com.finanzas.application.port.out.EncriptadorClaveOutPort;
import com.finanzas.application.port.out.GeneradorTokenOutPort;
import com.finanzas.application.port.out.UsuarioOutPort;
import com.finanzas.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacionService implements AutenticacionUseCase {

    private final UsuarioOutPort usuarioOutPort;
    private final EncriptadorClaveOutPort encriptadorClaveOutPort;
    private final GeneradorTokenOutPort generadorTokenOutPort;

    @Override
    public ResultadoAutenticacion registrar(ComandoRegistro comando) {
        if (usuarioOutPort.existePorCorreo(comando.correo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario(
                null,
                comando.correo(),
                encriptadorClaveOutPort.encriptar(comando.clave()),
                comando.monedaPrincipal(),
                LocalDateTime.now()
        );
        Usuario guardado = usuarioOutPort.guardar(usuario);
        String token = generadorTokenOutPort.generar(guardado.id(), guardado.correo());
        return new ResultadoAutenticacion(token, guardado.id(), guardado.correo(), guardado.monedaPrincipal());
    }

    @Override
    public ResultadoAutenticacion iniciarSesion(ComandoInicioSesion comando) {
        Usuario usuario = usuarioOutPort.buscarPorCorreo(comando.correo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!encriptadorClaveOutPort.coincide(comando.clave(), usuario.claveEncriptada())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = generadorTokenOutPort.generar(usuario.id(), usuario.correo());
        return new ResultadoAutenticacion(token, usuario.id(), usuario.correo(), usuario.monedaPrincipal());
    }
}
