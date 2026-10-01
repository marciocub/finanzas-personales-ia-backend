package com.finanzas.domain.model;

public class PresupuestoExcedidoException extends RuntimeException {

    public PresupuestoExcedidoException(String mensaje) {
        super(mensaje);
    }
}
