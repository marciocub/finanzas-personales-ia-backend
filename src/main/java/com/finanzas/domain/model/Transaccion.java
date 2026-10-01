package com.finanzas.domain.model;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.Objects;

public record Transaccion(
        Long id,
        Long usuarioId,
        Dinero dinero,
        TipoTransaccion tipo,
        Categoria categoria,
        LocalDateTime fecha,
        String descripcion
) {
    public Transaccion {
        Objects.requireNonNull(usuarioId, "El usuario es obligatorio");
        Objects.requireNonNull(dinero, "El dinero es obligatorio");
        Objects.requireNonNull(tipo, "El tipo de transacción es obligatorio");
        Objects.requireNonNull(categoria, "La categoría es obligatoria");
        Objects.requireNonNull(fecha, "La fecha es obligatoria");
    }

    public boolean esGasto() {
        return tipo == TipoTransaccion.GASTO;
    }

    public Transaccion conId(Long nuevoId) {
        return new Transaccion(nuevoId, usuarioId, dinero, tipo, categoria, fecha, descripcion);
    }
}
