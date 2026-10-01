package com.finanzas.application.service;

import com.finanzas.application.dto.ComandoCrearTransaccion;
import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.application.dto.ResumenTransacciones;
import com.finanzas.application.port.in.CrearTransaccionUseCase;
import com.finanzas.application.port.in.ObtenerResumenTransaccionesUseCase;
import com.finanzas.application.port.out.PresupuestoOutPort;
import com.finanzas.application.port.out.TransaccionOutPort;
import com.finanzas.domain.enumeration.TipoTransaccion;
import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Transaccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransaccionService implements CrearTransaccionUseCase, ObtenerResumenTransaccionesUseCase {

    private final TransaccionOutPort transaccionOutPort;
    private final PresupuestoOutPort presupuestoOutPort;

    @Override
    public Transaccion ejecutar(ComandoCrearTransaccion comando) {
        Transaccion transaccion = new Transaccion(
                null,
                comando.usuarioId(),
                Dinero.de(comando.monto(), comando.moneda()),
                comando.tipo(),
                comando.categoria(),
                comando.fecha() != null ? comando.fecha() : LocalDateTime.now(),
                comando.descripcion()
        );

        if (transaccion.esGasto()) {
            presupuestoOutPort
                    .buscarVigente(transaccion.usuarioId(), transaccion.categoria(), transaccion.fecha().toLocalDate())
                    .ifPresent(presupuesto -> {
                        BigDecimal totalGastado = transaccionOutPort.sumarGastosPorCategoriaYPeriodo(
                                transaccion.usuarioId(),
                                transaccion.categoria(),
                                presupuesto.fechaInicio().atStartOfDay(),
                                presupuesto.fechaFin().atTime(23, 59, 59)
                        );
                        presupuesto.excedeLimite(transaccion, totalGastado);
                    });
        }

        return transaccionOutPort.guardar(transaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumenTransacciones ejecutar(CriterioFiltroTransaccion criterio) {
        var transacciones = transaccionOutPort.buscarPorCriterio(criterio);

        BigDecimal totalIngresos = transacciones.stream()
                .filter(t -> t.tipo() == TipoTransaccion.INGRESO)
                .map(t -> t.dinero().monto())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGastos = transacciones.stream()
                .filter(t -> t.tipo() == TipoTransaccion.GASTO)
                .map(t -> t.dinero().monto())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumenTransacciones(
                totalIngresos,
                totalGastos,
                totalIngresos.subtract(totalGastos),
                transacciones
        );
    }
}
