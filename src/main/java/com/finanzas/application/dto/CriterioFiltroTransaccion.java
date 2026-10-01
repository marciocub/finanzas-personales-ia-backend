package com.finanzas.application.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Criterio neutro para filtrar transacciones. No depende de Spring.
 */
public record CriterioFiltroTransaccion(
        Long usuarioId,
        LocalDateTime fechaDesde,
        LocalDateTime fechaHasta,
        Categoria categoria,
        TipoTransaccion tipo,
        BigDecimal montoMinimo,
        BigDecimal montoMaximo
) {
}
