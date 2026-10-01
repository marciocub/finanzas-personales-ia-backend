package com.finanzas.application.port.out;

public interface GeneradorTokenOutPort {
    String generar(Long usuarioId, String correo);
}
