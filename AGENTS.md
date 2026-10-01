# CREAR AGENTS.MD - REGLAS DEL AGENTE

1. Todo el código (clases, métodos, variables, comentarios) DEBE ser en ESPAÑOL.
2. La carpeta `domain/` NUNCA debe importar nada de Spring, JPA, Hibernate o Lombok.
3. Las entidades JPA (`*EntidadJpa`) viven solo en `infrastructure/out/db/entity/`.
4. Todas las interfaces de puertos van en `application/port/in/` o `application/port/out/`.
5. Los puertos de salida reciben DTOs o modelos de dominio, nunca tipos de Spring (`Specification`, `Pageable` de infraestructura, etc.).
6. MapStruct vive en `infrastructure/mapper/`. Nunca se expone una entidad JPA al dominio ni a los controladores.
7. Lombok solo se usa en `application/` e `infrastructure/`.
8. Los endpoints REST, el script SQL y los nombres de tablas/columnas se escriben en español.
9. El paquete base del backend es `com.finanzas`.
10. Ante un nuevo caso de uso, seguir las plantillas de `finanzas-backend/docs/SKILLS.md` y registrar decisiones en `finanzas-backend/docs/MEMORY.md`.
