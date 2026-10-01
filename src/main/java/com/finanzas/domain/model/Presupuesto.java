package com.finanzas.domain.model;

import com.finanzas.domain.enumeration.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public record Presupuesto(
        Long id,
        Long usuarioId,
        Categoria categoria,
        Dinero montoLimite,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
    public Presupuesto {
        Objects.requireNonNull(usuarioId, "El usuario es obligatorio");
        Objects.requireNonNull(categoria, "La categoría es obligatoria");
        Objects.requireNonNull(montoLimite, "El monto límite es obligatorio");
        Objects.requireNonNull(fechaInicio, "La fecha de inicio es obligatoria");
        Objects.requireNonNull(fechaFin, "La fecha de fin es obligatoria");
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior al inicio");
        }
    }

    public boolean cubreFecha(LocalDateTime fecha) {
        LocalDate dia = fecha.toLocalDate();
        return !dia.isBefore(fechaInicio) && !dia.isAfter(fechaFin);
    }

    /**
     * Evalúa si un nuevo gasto supera el límite del presupuesto.
     *
     * @return mensaje de alerta si el gasto queda dentro del límite pero supera el 80%;
     *         lanza {@link PresupuestoExcedidoException} si lo supera.
     */
    public String excedeLimite(Transaccion transaccion, BigDecimal totalGastadoEnPeriodo) {
        Objects.requireNonNull(transaccion, "La transacción es obligatoria");
        Objects.requireNonNull(totalGastadoEnPeriodo, "El total gastado es obligatorio");

        if (!transaccion.esGasto()) {
            return null;
        }
        if (!categoria.equals(transaccion.categoria())) {
            return null;
        }
        if (!cubreFecha(transaccion.fecha())) {
            return null;
        }

        BigDecimal nuevoTotal = totalGastadoEnPeriodo.add(transaccion.dinero().monto());
        if (nuevoTotal.compareTo(montoLimite.monto()) > 0) {
            throw new PresupuestoExcedidoException(
                    "El gasto supera el límite del presupuesto de " + categoria
                            + ". Límite: " + montoLimite.monto()
                            + ", acumulado: " + nuevoTotal
            );
        }

        BigDecimal umbralAlerta = montoLimite.monto().multiply(new BigDecimal("0.80"));
        if (nuevoTotal.compareTo(umbralAlerta) >= 0) {
            return "Alerta: el gasto alcanza o supera el 80% del presupuesto de " + categoria;
        }
        return null;
    }

    public Presupuesto conId(Long nuevoId) {
        return new Presupuesto(nuevoId, usuarioId, categoria, montoLimite, fechaInicio, fechaFin);
    }
}
