package com.finanzas.infrastructure.in.web.dto;

import com.finanzas.domain.enumeration.Moneda;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitudRegistro(
        @NotBlank @Email String correo,
        @NotBlank @Size(min = 6) String clave,
        @NotNull Moneda monedaPrincipal
) {
}
