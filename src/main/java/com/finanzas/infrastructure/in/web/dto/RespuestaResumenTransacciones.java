package com.finanzas.infrastructure.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record RespuestaResumenTransacciones(
        BigDecimal totalIngresos,
        BigDecimal totalGastos,
        BigDecimal saldo,
        List<RespuestaTransaccion> transacciones
) {
}
