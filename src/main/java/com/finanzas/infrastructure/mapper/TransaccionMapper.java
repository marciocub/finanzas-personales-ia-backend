package com.finanzas.infrastructure.mapper;

import com.finanzas.domain.model.Dinero;
import com.finanzas.domain.model.Transaccion;
import com.finanzas.infrastructure.out.db.entity.TransaccionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {

    @Mapping(target = "dinero", expression = "java(aDinero(entidadJpa))")
    Transaccion aDominio(TransaccionJpaEntity entidadJpa);

    @Mapping(target = "monto", source = "dinero.monto")
    @Mapping(target = "moneda", source = "dinero.moneda")
    TransaccionJpaEntity aEntidadJpa(Transaccion dominio);

    default Dinero aDinero(TransaccionJpaEntity entidadJpa) {
        return Dinero.de(entidadJpa.getMonto(), entidadJpa.getMoneda());
    }
}
