package com.finanzas.domain.model;

import com.finanzas.domain.enumeration.Moneda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value object inmutable que representa una cantidad monetaria.
 */
public record Dinero(BigDecimal monto, Moneda moneda) {

    public Dinero {
        Objects.requireNonNull(monto, "El monto es obligatorio");
        Objects.requireNonNull(moneda, "La moneda es obligatoria");
        if (monto.scale() > 4) {
            monto = monto.setScale(4, RoundingMode.HALF_UP);
        }
        validarMonto(monto);
    }

    public static Dinero de(BigDecimal monto, Moneda moneda) {
        return new Dinero(monto, moneda);
    }

    public void validarMonto(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
    }

    public Dinero sumar(Dinero otro) {
        validarMismaMoneda(otro);
        return new Dinero(monto.add(otro.monto), moneda);
    }

    public Dinero restar(Dinero otro) {
        validarMismaMoneda(otro);
        BigDecimal resultado = monto.subtract(otro.monto);
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El resultado de restar no puede ser negativo");
        }
        return new Dinero(resultado, moneda);
    }

    public boolean esMayorQue(Dinero otro) {
        validarMismaMoneda(otro);
        return monto.compareTo(otro.monto) > 0;
    }

    private void validarMismaMoneda(Dinero otro) {
        Objects.requireNonNull(otro, "El dinero a comparar es obligatorio");
        if (moneda != otro.moneda) {
            throw new IllegalArgumentException("No se pueden operar montos de distintas monedas");
        }
    }
}
