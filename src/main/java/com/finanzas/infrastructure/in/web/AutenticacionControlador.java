package com.finanzas.infrastructure.in.web;

import com.finanzas.application.dto.ComandoInicioSesion;
import com.finanzas.application.dto.ComandoRegistro;
import com.finanzas.application.port.in.AutenticacionCasoUso;
import com.finanzas.infrastructure.in.web.dto.RespuestaAutenticacion;
import com.finanzas.infrastructure.in.web.dto.SolicitudInicioSesion;
import com.finanzas.infrastructure.in.web.dto.SolicitudRegistro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacion")
@RequiredArgsConstructor
public class AutenticacionControlador {

    private final AutenticacionCasoUso autenticacionCasoUso;

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaAutenticacion registrar(@Valid @RequestBody SolicitudRegistro solicitud) {
        var resultado = autenticacionCasoUso.registrar(
                new ComandoRegistro(solicitud.correo(), solicitud.clave(), solicitud.monedaPrincipal())
        );
        return new RespuestaAutenticacion(
                resultado.token(), resultado.usuarioId(), resultado.correo(), resultado.monedaPrincipal()
        );
    }

    @PostMapping("/inicio-sesion")
    public RespuestaAutenticacion iniciarSesion(@Valid @RequestBody SolicitudInicioSesion solicitud) {
        var resultado = autenticacionCasoUso.iniciarSesion(
                new ComandoInicioSesion(solicitud.correo(), solicitud.clave())
        );
        return new RespuestaAutenticacion(
                resultado.token(), resultado.usuarioId(), resultado.correo(), resultado.monedaPrincipal()
        );
    }
}
