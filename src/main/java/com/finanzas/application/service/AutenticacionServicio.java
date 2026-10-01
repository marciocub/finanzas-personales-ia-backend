package com.finanzas.application.service;

import com.finanzas.application.dto.ComandoInicioSesion;
import com.finanzas.application.dto.ComandoRegistro;
import com.finanzas.application.dto.ResultadoAutenticacion;
import com.finanzas.application.port.in.AutenticacionCasoUso;
import com.finanzas.application.port.out.EncriptadorClavePuertoSalida;
import com.finanzas.application.port.out.GeneradorTokenPuertoSalida;
import com.finanzas.application.port.out.UsuarioPuertoSalida;
import com.finanzas.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacionServicio implements AutenticacionCasoUso {

    private final UsuarioPuertoSalida usuarioPuertoSalida;
    private final EncriptadorClavePuertoSalida encriptadorClavePuertoSalida;
    private final GeneradorTokenPuertoSalida generadorTokenPuertoSalida;

    @Override
    public ResultadoAutenticacion registrar(ComandoRegistro comando) {
        if (usuarioPuertoSalida.existePorCorreo(comando.correo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        Usuario usuario = new Usuario(
                null,
                comando.correo(),
                encriptadorClavePuertoSalida.encriptar(comando.clave()),
                comando.monedaPrincipal(),
                LocalDateTime.now()
        );
        Usuario guardado = usuarioPuertoSalida.guardar(usuario);
        String token = generadorTokenPuertoSalida.generar(guardado.id(), guardado.correo());
        return new ResultadoAutenticacion(token, guardado.id(), guardado.correo(), guardado.monedaPrincipal());
    }

    @Override
    public ResultadoAutenticacion iniciarSesion(ComandoInicioSesion comando) {
        Usuario usuario = usuarioPuertoSalida.buscarPorCorreo(comando.correo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!encriptadorClavePuertoSalida.coincide(comando.clave(), usuario.claveEncriptada())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = generadorTokenPuertoSalida.generar(usuario.id(), usuario.correo());
        return new ResultadoAutenticacion(token, usuario.id(), usuario.correo(), usuario.monedaPrincipal());
    }
}
