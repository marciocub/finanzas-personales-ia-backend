package com.finanzas.infrastructure.out.db;

import com.finanzas.application.port.out.PresupuestoOutPort;
import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.model.Presupuesto;
import com.finanzas.infrastructure.mapper.PresupuestoMapper;
import com.finanzas.infrastructure.out.db.repository.PresupuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PresupuestoDbAdapter implements PresupuestoOutPort {

    private final PresupuestoRepository presupuestoRepository;
    private final PresupuestoMapper presupuestoMapper;

    @Override
    public Presupuesto guardar(Presupuesto presupuesto) {
        var entidad = presupuestoMapper.aEntidadJpa(presupuesto);
        return presupuestoMapper.aDominio(presupuestoRepository.save(entidad));
    }

    @Override
    public List<Presupuesto> buscarPorUsuario(Long usuarioId) {
        return presupuestoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(presupuestoMapper::aDominio)
                .toList();
    }

    @Override
    public Optional<Presupuesto> buscarVigente(Long usuarioId, Categoria categoria, LocalDate fecha) {
        return presupuestoRepository
                .findFirstByUsuarioIdAndCategoriaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        usuarioId, categoria, fecha, fecha
                )
                .map(presupuestoMapper::aDominio);
    }
}
