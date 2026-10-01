package com.finanzas.infrastructure.in.web;

import com.finanzas.application.dto.ComandoCrearPresupuesto;
import com.finanzas.application.port.in.GestionarPresupuestoUseCase;
import com.finanzas.infrastructure.in.web.dto.RespuestaPresupuesto;
import com.finanzas.infrastructure.in.web.dto.SolicitudCrearPresupuesto;
import com.finanzas.infrastructure.security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
@RequiredArgsConstructor
public class PresupuestoController {

    private final GestionarPresupuestoUseCase gestionarPresupuestoUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaPresupuesto crear(
            @AuthenticationPrincipal UsuarioAutenticado usuario,
            @Valid @RequestBody SolicitudCrearPresupuesto solicitud
    ) {
        var presupuesto = gestionarPresupuestoUseCase.crear(new ComandoCrearPresupuesto(
                usuario.id(),
                solicitud.categoria(),
                solicitud.montoLimite(),
                solicitud.moneda(),
                solicitud.fechaInicio(),
                solicitud.fechaFin()
        ));
        return new RespuestaPresupuesto(
                presupuesto.id(),
                presupuesto.usuarioId(),
                presupuesto.categoria(),
                presupuesto.montoLimite().monto(),
                presupuesto.montoLimite().moneda(),
                presupuesto.fechaInicio(),
                presupuesto.fechaFin()
        );
    }

    @GetMapping
    public List<RespuestaPresupuesto> listar(@AuthenticationPrincipal UsuarioAutenticado usuario) {
        return gestionarPresupuestoUseCase.listarPorUsuario(usuario.id()).stream()
                .map(p -> new RespuestaPresupuesto(
                        p.id(),
                        p.usuarioId(),
                        p.categoria(),
                        p.montoLimite().monto(),
                        p.montoLimite().moneda(),
                        p.fechaInicio(),
                        p.fechaFin()
                ))
                .toList();
    }
}
