package com.finanzas.application.service;

import com.finanzas.application.dto.ComandoCrearPresupuesto;
import com.finanzas.application.port.in.GestionarPresupuestoCasoUso;
import com.finanzas.application.port.out.PresupuestoPuertoSalida;
import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Presupuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PresupuestoServicio implements GestionarPresupuestoCasoUso {

    private final PresupuestoPuertoSalida presupuestoPuertoSalida;

    @Override
    public Presupuesto crear(ComandoCrearPresupuesto comando) {
        Presupuesto presupuesto = new Presupuesto(
                null,
                comando.usuarioId(),
                comando.categoria(),
                Dinero.de(comando.montoLimite(), comando.moneda()),
                comando.fechaInicio(),
                comando.fechaFin()
        );
        return presupuestoPuertoSalida.guardar(presupuesto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Presupuesto> listarPorUsuario(Long usuarioId) {
        return presupuestoPuertoSalida.buscarPorUsuario(usuarioId);
    }
}
