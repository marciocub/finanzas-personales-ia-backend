package com.finanzas.infrastructure.out.db.repository;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.infrastructure.out.db.entity.PresupuestoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresupuestoRepository extends JpaRepository<PresupuestoJpaEntity, Long> {

    List<PresupuestoJpaEntity> findByUsuarioId(Long usuarioId);

    Optional<PresupuestoJpaEntity> findFirstByUsuarioIdAndCategoriaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long usuarioId,
            Categoria categoria,
            LocalDate fechaInicio,
            LocalDate fechaFin
    );
}
