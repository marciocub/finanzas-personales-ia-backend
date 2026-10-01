package com.finanzas.application.port.in;

import com.finanzas.application.dto.ComandoCrearTransaccion;
import com.finanzas.domain.model.Transaccion;

public interface CrearTransaccionUseCase {
    Transaccion ejecutar(ComandoCrearTransaccion comando);
}
