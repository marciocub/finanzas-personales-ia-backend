package com.finanzas.application.port.out;

import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.model.Transaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionOutPort {
    Transaccion guardar(Transaccion transaccion);

    List<Transaccion> buscarPorCriterio(CriterioFiltroTransaccion criterio);

    BigDecimal sumarGastosPorCategoriaYPeriodo(
            Long usuarioId,
            Categoria categoria,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    );
}
