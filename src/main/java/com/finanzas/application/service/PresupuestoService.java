package com.finanzas.application.service;

import com.finanzas.application.dto.ComandoCrearPresupuesto;
import com.finanzas.application.port.in.GestionarPresupuestoUseCase;
import com.finanzas.application.port.out.PresupuestoOutPort;
import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Presupuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PresupuestoService implements GestionarPresupuestoUseCase {

    private final PresupuestoOutPort presupuestoOutPort;

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
        return presupuestoOutPort.guardar(presupuesto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Presupuesto> listarPorUsuario(Long usuarioId) {
        return presupuestoOutPort.buscarPorUsuario(usuarioId);
    }
}
