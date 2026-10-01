package com.finanzas.domain.model;

import com.finanzas.domain.enumeration.Moneda;

import java.time.LocalDateTime;
import java.util.Objects;

public record Usuario(
        Long id,
        String correo,
        String claveEncriptada,
        Moneda monedaPrincipal,
        LocalDateTime fechaCreacion
) {
    public Usuario {
        Objects.requireNonNull(correo, "El correo es obligatorio");
        Objects.requireNonNull(claveEncriptada, "La clave encriptada es obligatoria");
        Objects.requireNonNull(monedaPrincipal, "La moneda principal es obligatoria");
        Objects.requireNonNull(fechaCreacion, "La fecha de creación es obligatoria");
        if (correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
    }

    public Usuario conId(Long nuevoId) {
        return new Usuario(nuevoId, correo, claveEncriptada, monedaPrincipal, fechaCreacion);
    }
}
