# MEMORY.md - Decisiones de arquitectura

## Contexto

Gestor de finanzas personales con arquitectura hexagonal. Backend Java 21 / Spring Boot 3. Frontend React (Vite). Persistencia MySQL 8.

## Decisiones

- Paquete raíz: `com.finanzas`.
- Convención de nombres: los términos de negocio y los DTOs se escriben en español; los términos arquitectónicos se escriben en inglés (`*UseCase`, `*OutPort`, `*Service`, `*Controller`, `*DbAdapter`, `*JpaEntity`, `*Repository`, `*Mapper`, `Application`).
- El dominio usa records y clases inmutables sin Lombok.
- `Dinero` es un value object con `BigDecimal` y `Moneda`; las operaciones fallan si las monedas no coinciden.
- Un presupuesto se evalúa contra el total de gastos de la misma categoría y usuario en el período. Si el nuevo gasto lo excede, se lanza `PresupuestoExcedidoException` (HTTP 409 en infraestructura).
- El filtrado dinámico de transacciones usa un DTO de criterios en aplicación; las JPA Specifications quedan encapsuladas en `infrastructure/out/db/spec/`.
- JWT se guarda en `localStorage` del frontend y se envía con el interceptor de Axios.
- Docker: MySQL en el puerto **3310**, backend **8080**, frontend **3000**.
- Hibernate no hace `ddl-auto=update` en producción: el esquema vive en `script-creacion.sql` montado al contenedor MySQL.
