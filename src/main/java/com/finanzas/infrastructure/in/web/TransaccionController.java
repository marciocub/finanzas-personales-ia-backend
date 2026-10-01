package com.finanzas.infrastructure.in.web;

import com.finanzas.application.dto.ComandoCrearTransaccion;
import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.application.port.in.CrearTransaccionUseCase;
import com.finanzas.application.port.in.ObtenerResumenTransaccionesUseCase;
import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.TipoTransaccion;
import com.finanzas.infrastructure.in.web.dto.RespuestaResumenTransacciones;
import com.finanzas.infrastructure.in.web.dto.RespuestaTransaccion;
import com.finanzas.infrastructure.in.web.dto.SolicitudCrearTransaccion;
import com.finanzas.infrastructure.in.web.mapper.TransaccionWebMapper;
import com.finanzas.infrastructure.security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final CrearTransaccionUseCase crearTransaccionUseCase;
    private final ObtenerResumenTransaccionesUseCase obtenerResumenTransaccionesUseCase;
    private final TransaccionWebMapper transaccionWebMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaTransaccion crear(
            @AuthenticationPrincipal UsuarioAutenticado usuario,
            @Valid @RequestBody SolicitudCrearTransaccion solicitud
    ) {
        var comando = new ComandoCrearTransaccion(
                usuario.id(),
                solicitud.monto(),
                solicitud.moneda(),
                solicitud.tipo(),
                solicitud.categoria(),
                solicitud.fecha(),
                solicitud.descripcion()
        );
        return transaccionWebMapper.aRespuesta(crearTransaccionUseCase.ejecutar(comando));
    }

    @GetMapping
    public List<RespuestaTransaccion> listar(
            @AuthenticationPrincipal UsuarioAutenticado usuario,
            @RequestParam(required = false) LocalDateTime fechaDesde,
            @RequestParam(required = false) LocalDateTime fechaHasta,
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) TipoTransaccion tipo,
            @RequestParam(required = false) BigDecimal montoMinimo,
            @RequestParam(required = false) BigDecimal montoMaximo
    ) {
        var criterio = new CriterioFiltroTransaccion(
                usuario.id(), fechaDesde, fechaHasta, categoria, tipo, montoMinimo, montoMaximo
        );
        return obtenerResumenTransaccionesUseCase.ejecutar(criterio)
                .transacciones()
                .stream()
                .map(transaccionWebMapper::aRespuesta)
                .toList();
    }

    @GetMapping("/resumen")
    public RespuestaResumenTransacciones resumen(
            @AuthenticationPrincipal UsuarioAutenticado usuario,
            @RequestParam(required = false) LocalDateTime fechaDesde,
            @RequestParam(required = false) LocalDateTime fechaHasta,
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) TipoTransaccion tipo
    ) {
        var criterio = new CriterioFiltroTransaccion(
                usuario.id(), fechaDesde, fechaHasta, categoria, tipo, null, null
        );
        return transaccionWebMapper.aRespuestaResumen(obtenerResumenTransaccionesUseCase.ejecutar(criterio));
    }
}
