package com.finanzas.infrastructure.mapper;

import com.finanzas.domain.model.Usuario;
import com.finanzas.infrastructure.out.db.entity.UsuarioEntidadJpa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario aDominio(UsuarioEntidadJpa entidadJpa);

    UsuarioEntidadJpa aEntidadJpa(Usuario dominio);
}
