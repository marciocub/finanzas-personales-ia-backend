package com.finanzas.application.dto;

import com.finanzas.domain.enumeration.Moneda;

public record ComandoRegistro(
        String correo,
        String clave,
        Moneda monedaPrincipal
) {
}
