package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;
import com.finanzas.domain.enumeration.TipoTransaccion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SolicitudCrearTransaccion(
        @NotNull @DecimalMin("0.01") BigDecimal monto,
        @NotNull Moneda moneda,
        @NotNull TipoTransaccion tipo,
        @NotNull Categoria categoria,
        LocalDateTime fecha,
        String descripcion
) {
}
