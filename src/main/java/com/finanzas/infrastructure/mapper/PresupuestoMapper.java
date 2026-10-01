package com.finanzas.infrastructure.mapper;

import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Presupuesto;
import com.finanzas.infrastructure.out.db.entity.PresupuestoEntidadJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PresupuestoMapper {

    @Mapping(target = "montoLimite", expression = "java(aDinero(entidadJpa))")
    Presupuesto aDominio(PresupuestoEntidadJpa entidadJpa);

    @Mapping(target = "montoLimite", source = "montoLimite.monto")
    @Mapping(target = "moneda", source = "montoLimite.moneda")
    PresupuestoEntidadJpa aEntidadJpa(Presupuesto dominio);

    default Dinero aDinero(PresupuestoEntidadJpa entidadJpa) {
        return Dinero.de(entidadJpa.getMontoLimite(), entidadJpa.getMoneda());
    }
}
