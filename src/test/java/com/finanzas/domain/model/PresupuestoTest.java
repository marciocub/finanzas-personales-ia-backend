package com.finanzas.domain.model;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.Moneda;
import com.finanzas.domain.enumeration.TipoTransaccion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PresupuestoTest {

    @Test
    void lanzaExcepcionSiElGastoSuperaElLimite() {
        Presupuesto presupuesto = new Presupuesto(
                1L,
                1L,
                Categoria.ALIMENTACION,
                Dinero.de(new BigDecimal("100"), Moneda.ARS),
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 31)
        );
        Transaccion gasto = new Transaccion(
                null,
                1L,
                Dinero.de(new BigDecimal("40"), Moneda.ARS),
                TipoTransaccion.GASTO,
                Categoria.ALIMENTACION,
                LocalDateTime.of(2026, 10, 5, 12, 0),
                "supermercado"
        );

        assertThrows(PresupuestoExcedidoException.class, () ->
                presupuesto.excedeLimite(gasto, new BigDecimal("70"))
        );
    }

    @Test
    void alertaCuandoSeAlcanzaElOchentaPorCiento() {
        Presupuesto presupuesto = new Presupuesto(
                1L,
                1L,
                Categoria.ALIMENTACION,
                Dinero.de(new BigDecimal("100"), Moneda.ARS),
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 31)
        );
        Transaccion gasto = new Transaccion(
                null,
                1L,
                Dinero.de(new BigDecimal("10"), Moneda.ARS),
                TipoTransaccion.GASTO,
                Categoria.ALIMENTACION,
                LocalDateTime.of(2026, 10, 5, 12, 0),
                "pan"
        );

        String alerta = presupuesto.excedeLimite(gasto, new BigDecimal("75"));
        assertEquals("Alerta: el gasto alcanza o supera el 80% del presupuesto de ALIMENTACION", alerta);
    }
}
