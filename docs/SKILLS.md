# SKILLS.MD - PLANTILLAS

### Plantilla de Caso de Uso

```java
package com.finanzas.application.port.in;

public interface [Nombre]CasoUso {
    [Resultado] ejecutar([Comando] comando);
}
```

### Plantilla de Puerto de Salida

```java
package com.finanzas.application.port.out;

public interface [Nombre]PuertoSalida {
    [Dominio] guardar([Dominio] dominio);
}
```

### Plantilla de Servicio de Aplicación

```java
package com.finanzas.application.service;

import com.finanzas.application.port.in.[Nombre]CasoUso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class [Nombre]Servicio implements [Nombre]CasoUso {

    @Override
    public [Resultado] ejecutar([Comando] comando) {
        throw new UnsupportedOperationException("Pendiente de implementación");
    }
}
```

### Plantilla de Mapper MapStruct

```java
package com.finanzas.infrastructure.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface [Nombre]Mapper {
    [Dominio] aDominio([EntidadJpa] entidadJpa);
    [EntidadJpa] aEntidadJpa([Dominio] dominio);
}
```

### Plantilla de Entidad JPA

```java
package com.finanzas.infrastructure.out.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[tabla]")
@Getter
@Setter
@NoArgsConstructor
public class [Nombre]EntidadJpa {
}
```

### Plantilla de Controlador REST

```java
package com.finanzas.infrastructure.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/[recurso]")
@RequiredArgsConstructor
public class [Nombre]Controlador {
}
```
