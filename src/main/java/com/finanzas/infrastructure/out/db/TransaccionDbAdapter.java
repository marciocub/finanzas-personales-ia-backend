package com.finanzas.infrastructure.out.db;

import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.application.port.out.TransaccionOutPort;
import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.TipoTransaccion;
import com.finanzas.domain.model.Transaccion;
import com.finanzas.infrastructure.mapper.TransaccionMapper;
import com.finanzas.infrastructure.out.db.repository.TransaccionRepository;
import com.finanzas.infrastructure.out.db.spec.TransaccionEspecificacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransaccionDbAdapter implements TransaccionOutPort {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        var entidad = transaccionMapper.aEntidadJpa(transaccion);
        var guardada = transaccionRepository.save(entidad);
        return transaccionMapper.aDominio(guardada);
    }

    @Override
    public List<Transaccion> buscarPorCriterio(CriterioFiltroTransaccion criterio) {
        return transaccionRepository
                .findAll(TransaccionEspecificacion.desdeCriterio(criterio))
                .stream()
                .map(transaccionMapper::aDominio)
                .toList();
    }

    @Override
    public BigDecimal sumarGastosPorCategoriaYPeriodo(
            Long usuarioId,
            Categoria categoria,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    ) {
        return transaccionRepository.sumarMontoPorFiltro(
                usuarioId,
                categoria,
                TipoTransaccion.GASTO,
                fechaDesde,
                fechaHasta
        );
    }
}
