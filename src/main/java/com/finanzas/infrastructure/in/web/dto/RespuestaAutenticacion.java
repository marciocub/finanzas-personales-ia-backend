package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Moneda;

public record RespuestaAutenticacion(
        String token,
        Long usuarioId,
        String correo,
        Moneda monedaPrincipal
) {
}
