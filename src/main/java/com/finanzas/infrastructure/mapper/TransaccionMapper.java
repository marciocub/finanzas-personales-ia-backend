package com.finanzas.infrastructure.mapper;

import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Transaccion;
import com.finanzas.infrastructure.out.db.entity.TransaccionEntidadJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {

    @Mapping(target = "dinero", expression = "java(aDinero(entidadJpa))")
    Transaccion aDominio(TransaccionEntidadJpa entidadJpa);

    @Mapping(target = "monto", source = "dinero.monto")
    @Mapping(target = "moneda", source = "dinero.moneda")
    TransaccionEntidadJpa aEntidadJpa(Transaccion dominio);

    default Dinero aDinero(TransaccionEntidadJpa entidadJpa) {
        return Dinero.de(entidadJpa.getMonto(), entidadJpa.getMoneda());
    }
}
