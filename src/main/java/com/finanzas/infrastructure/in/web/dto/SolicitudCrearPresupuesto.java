package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SolicitudCrearPresupuesto(
        @NotNull Categoria categoria,
        @NotNull @DecimalMin("0.01") BigDecimal montoLimite,
        @NotNull Moneda moneda,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFin
) {
}
