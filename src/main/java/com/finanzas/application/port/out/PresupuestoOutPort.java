package com.finanzas.application.port.out;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.model.Presupuesto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresupuestoOutPort {
    Presupuesto guardar(Presupuesto presupuesto);

    List<Presupuesto> buscarPorUsuario(Long usuarioId);

    Optional<Presupuesto> buscarVigente(Long usuarioId, Categoria categoria, LocalDate fecha);
}
