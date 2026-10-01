package com.finanzas.infrastructure.out.db.repository;

import com.finanzas.domain.enumeration.Categoria;
import com.finanzas.domain.enumeration.TipoTransaccion;
import com.finanzas.infrastructure.out.db.entity.TransaccionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransaccionRepository extends JpaRepository<TransaccionJpaEntity, Long>,
        JpaSpecificationExecutor<TransaccionJpaEntity> {

    @Query("""
            SELECT COALESCE(SUM(t.monto), 0)
            FROM TransaccionJpaEntity t
            WHERE t.usuarioId = :usuarioId
              AND t.categoria = :categoria
              AND t.tipo = :tipo
              AND t.fecha BETWEEN :desde AND :hasta
            """)
    BigDecimal sumarMontoPorFiltro(
            @Param("usuarioId") Long usuarioId,
            @Param("categoria") Categoria categoria,
            @Param("tipo") TipoTransaccion tipo,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta
    );
}
