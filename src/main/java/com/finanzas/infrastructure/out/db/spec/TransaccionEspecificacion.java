package com.finanzas.infrastructure.out.db.spec;

import com.finanzas.application.dto.CriterioFiltroTransaccion;
import com.finanzas.infrastructure.out.db.entity.TransaccionEntidadJpa;
import org.springframework.data.jpa.domain.Specification;

public final class TransaccionEspecificacion {

    private TransaccionEspecificacion() {
    }

    public static Specification<TransaccionEntidadJpa> desdeCriterio(CriterioFiltroTransaccion criterio) {
        return Specification
                .where(porUsuario(criterio.usuarioId()))
                .and(porRangoFechas(criterio))
                .and(porCategoria(criterio))
                .and(porTipo(criterio))
                .and(porRangoMontos(criterio));
    }

    private static Specification<TransaccionEntidadJpa> porUsuario(Long usuarioId) {
        return (raiz, consulta, constructor) -> constructor.equal(raiz.get("usuarioId"), usuarioId);
    }

    private static Specification<TransaccionEntidadJpa> porRangoFechas(CriterioFiltroTransaccion criterio) {
        return (raiz, consulta, constructor) -> {
            if (criterio.fechaDesde() != null && criterio.fechaHasta() != null) {
                return constructor.between(raiz.get("fecha"), criterio.fechaDesde(), criterio.fechaHasta());
            }
            if (criterio.fechaDesde() != null) {
                return constructor.greaterThanOrEqualTo(raiz.get("fecha"), criterio.fechaDesde());
            }
            if (criterio.fechaHasta() != null) {
                return constructor.lessThanOrEqualTo(raiz.get("fecha"), criterio.fechaHasta());
            }
            return constructor.conjunction();
        };
    }

    private static Specification<TransaccionEntidadJpa> porCategoria(CriterioFiltroTransaccion criterio) {
        return (raiz, consulta, constructor) -> criterio.categoria() == null
                ? constructor.conjunction()
                : constructor.equal(raiz.get("categoria"), criterio.categoria());
    }

    private static Specification<TransaccionEntidadJpa> porTipo(CriterioFiltroTransaccion criterio) {
        return (raiz, consulta, constructor) -> criterio.tipo() == null
                ? constructor.conjunction()
                : constructor.equal(raiz.get("tipo"), criterio.tipo());
    }

    private static Specification<TransaccionEntidadJpa> porRangoMontos(CriterioFiltroTransaccion criterio) {
        return (raiz, consulta, constructor) -> {
            if (criterio.montoMinimo() != null && criterio.montoMaximo() != null) {
                return constructor.between(raiz.get("monto"), criterio.montoMinimo(), criterio.montoMaximo());
            }
            if (criterio.montoMinimo() != null) {
                return constructor.greaterThanOrEqualTo(raiz.get("monto"), criterio.montoMinimo());
            }
            if (criterio.montoMaximo() != null) {
                return constructor.lessThanOrEqualTo(raiz.get("monto"), criterio.montoMaximo());
            }
            return constructor.conjunction();
        };
    }
}
