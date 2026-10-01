package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RespuestaPresupuesto(
        Long id,
        Long usuarioId,
        Categoria categoria,
        BigDecimal montoLimite,
        Moneda moneda,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
}
