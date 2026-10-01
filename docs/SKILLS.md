# SKILLS.MD - PLANTILLAS

> Convención de nombres: los términos de negocio y los DTOs se escriben en español; los términos arquitectónicos se escriben en inglés (`UseCase`, `OutPort`, `InPort`, `Service`, `Controller`, `Adapter`, `JpaEntity`, `Repository`, `Mapper`, `Application`).

### Plantilla de Caso de Uso (puerto de entrada)

```java
package com.finanzas.application.port.in;

public interface [Nombre]UseCase {
    [Resultado] ejecutar([Comando] comando);
}
```

### Plantilla de Puerto de Salida

```java
package com.finanzas.application.port.out;

public interface [Nombre]OutPort {
    [Dominio] guardar([Dominio] dominio);
}
```

### Plantilla de Servicio de Aplicación

```java
package com.finanzas.application.service;

import com.finanzas.application.port.in.[Nombre]UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class [Nombre]Service implements [Nombre]UseCase {

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
    [Dominio] aDominio([Nombre]JpaEntity entidadJpa);
    [Nombre]JpaEntity aEntidadJpa([Dominio] dominio);
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
public class [Nombre]JpaEntity {
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
public class [Nombre]Controller {
}
```

### Plantilla de Adaptador de Salida

```java
package com.finanzas.infrastructure.out.db;

import com.finanzas.application.port.out.[Nombre]OutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class [Nombre]DbAdapter implements [Nombre]OutPort {
}
```
