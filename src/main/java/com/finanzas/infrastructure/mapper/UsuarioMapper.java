package com.finanzas.infrastructure.mapper;

import com.finanzas.domain.model.Usuario;
import com.finanzas.infrastructure.out.db.entity.UsuarioJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario aDominio(UsuarioJpaEntity entidadJpa);

    UsuarioJpaEntity aEntidadJpa(Usuario dominio);
}
