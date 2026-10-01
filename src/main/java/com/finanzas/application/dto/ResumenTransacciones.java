package com.finanzas.application.dto;

import com.finanzas.domain.model.Transaccion;

import java.math.BigDecimal;
import java.util.List;

public record ResumenTransacciones(
        BigDecimal totalIngresos,
        BigDecimal totalGastos,
        BigDecimal saldo,
        List<Transaccion> transacciones
) {
}
