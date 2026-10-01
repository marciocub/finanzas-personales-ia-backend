package com.finanzas.infrastructure.out.db.repository;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.infrastructure.out.db.entity.PresupuestoEntidadJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresupuestoRepositorioJpa extends JpaRepository<PresupuestoEntidadJpa, Long> {

    List<PresupuestoEntidadJpa> findByUsuarioId(Long usuarioId);

    Optional<PresupuestoEntidadJpa> findFirstByUsuarioIdAndCategoriaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long usuarioId,
            Categoria categoria,
            LocalDate fechaInicio,
            LocalDate fechaFin
    );
}
