package com.finanzas.application.dto;

import com.finanzas.domain.enumeration.Moneda;

public record ResultadoAutenticacion(
        String token,
        Long usuarioId,
        String correo,
        Moneda monedaPrincipal
) {
}
