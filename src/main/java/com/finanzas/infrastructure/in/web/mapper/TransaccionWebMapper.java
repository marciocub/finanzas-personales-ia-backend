package com.finanzas.infrastructure.in.web.mapper;

import com.finanzas.application.dto.ResumenTransacciones;
import com.finanzas.domain.model.Transaccion;
import com.finanzas.infrastructure.in.web.dto.RespuestaResumenTransacciones;
import com.finanzas.infrastructure.in.web.dto.RespuestaTransaccion;
import org.springframework.stereotype.Component;

@Component
public class TransaccionWebMapper {

    public RespuestaTransaccion aRespuesta(Transaccion transaccion) {
        return new RespuestaTransaccion(
                transaccion.id(),
                transaccion.usuarioId(),
                transaccion.dinero().monto(),
                transaccion.dinero().moneda(),
                transaccion.tipo(),
                transaccion.categoria(),
                transaccion.fecha(),
                transaccion.descripcion()
        );
    }

    public RespuestaResumenTransacciones aRespuestaResumen(ResumenTransacciones resumen) {
        return new RespuestaResumenTransacciones(
                resumen.totalIngresos(),
                resumen.totalGastos(),
                resumen.saldo(),
                resumen.transacciones().stream().map(this::aRespuesta).toList()
        );
    }
}
