package com.finanzas.application.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;
import com.finanzas.domain.enumeration.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComandoCrearTransaccion(
        Long usuarioId,
        BigDecimal monto,
        Moneda moneda,
        TipoTransaccion tipo,
        Categoria categoria,
        LocalDateTime fecha,
        String descripcion
) {
}
