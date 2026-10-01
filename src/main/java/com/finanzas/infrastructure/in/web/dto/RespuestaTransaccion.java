package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;
import com.finanzas.domain.enumeration.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RespuestaTransaccion(
        Long id,
        Long usuarioId,
        BigDecimal monto,
        Moneda moneda,
        TipoTransaccion tipo,
        Categoria categoria,
        LocalDateTime fecha,
        String descripcion
) {
}
