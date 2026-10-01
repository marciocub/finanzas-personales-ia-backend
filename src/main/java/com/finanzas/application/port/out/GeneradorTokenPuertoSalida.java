package com.finanzas.application.port.out;

public interface GeneradorTokenPuertoSalida {
    String generar(Long usuarioId, String correo);
}
