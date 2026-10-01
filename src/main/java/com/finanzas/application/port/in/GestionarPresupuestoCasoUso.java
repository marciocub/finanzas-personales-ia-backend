package com.finanzas.application.port.in;

import com.finanzas.application.dto.ComandoCrearPresupuesto;
import com.finanzas.domain.model.Presupuesto;

import java.util.List;

public interface GestionarPresupuestoCasoUso {
    Presupuesto crear(ComandoCrearPresupuesto comando);

    List<Presupuesto> listarPorUsuario(Long usuarioId);
}
