package com.finanzas.application.port.in;

import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.application.dto.ResumenTransacciones;

public interface ObtenerResumenTransaccionesCasoUso {
    ResumenTransacciones ejecutar(CriterioFiltroTransaccion criterio);
}
