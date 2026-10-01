package com.finanzas.infrastructure.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record SolicitudInicioSesion(
        @NotBlank String correo,
        @NotBlank String clave
) {
}
