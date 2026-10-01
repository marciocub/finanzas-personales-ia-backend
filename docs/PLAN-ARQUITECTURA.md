# Plan de arquitectura y especificación técnica

Gestor de finanzas personales y presupuestos. Arquitectura hexagonal, Java 21, Spring Boot 3, React (Vite).

## 1. Estructura de paquetes

```text
finanzas-personales-ia/
├── AGENTS.md
├── docker-compose.yml
├── finanzas-backend/          ← API Spring Boot
│   ├── docs/                  ← MEMORY.md, PLAN-ARQUITECTURA.md, SKILLS.md
│   └── documentacion/         ← documentacion-tecnica.md
└── finanzas-frontend/         ← SPA React
```

Backend (`com.finanzas`):

- `domain/` — Java 21 puro: modelo y enumeraciones.
- `application/` — puertos (interfaces), servicios, DTOs.
- `infrastructure/` — REST, JPA, MapStruct, seguridad, configuración.

## 2. Reglas inviolables

- `domain/` sin Spring, JPA, Lombok ni dependencias externas.
- `port/in` y `port/out` solo interfaces.
- Entidades JPA solo en `infrastructure/out/db/entity/`.
- Specifications solo en `infrastructure/out/db/spec/`.
- Mapeo dominio ↔ JPA con MapStruct.

## 3. Stack

| Capa | Tecnología |
|------|------------|
| Backend | Java 21, Spring Boot 3, Data JPA, Security, JWT, MapStruct, Lombok |
| Base de datos | MySQL 8.0 (puerto host 3310) |
| Frontend | React + Vite, Tailwind, Lucide, Axios, Recharts |
| Entorno | Docker Compose |

## 4. Modelo de negocio

- **Usuario**: identidad, correo, clave encriptada, moneda principal.
- **Dinero**: monto + moneda; sumar, restar, validar.
- **Transaccion**: ingreso o gasto categorizado.
- **Presupuesto**: límite por categoría y período.
- **Regla**: un gasto no puede superar el límite del presupuesto vigente; si lo hace, `PresupuestoExcedidoException`.

## 5. API REST (nombres en español)

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/autenticacion/registro` | Alta de usuario |
| POST | `/api/autenticacion/inicio-sesion` | JWT |
| GET | `/api/transacciones` | Listado con filtros (specifications) |
| POST | `/api/transacciones` | Alta (valida presupuesto si es gasto) |
| GET | `/api/transacciones/resumen` | Totales e ingresos/gastos |
| GET/POST | `/api/presupuestos` | Consulta y alta de presupuestos |

## 6. Frontend

- `clienteAxios.js` — Bearer JWT.
- `ContextoAutenticacion.jsx` — sesión.
- `PanelPrincipal.jsx` — tarjetas y gráfico gastos vs límite.
- `Transacciones.jsx` — tabla y filtros conectados a la API.

## 7. Arranque

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- MySQL: localhost:3310 (usuario `finanzas`, clave `finanzas`, base `finanzas_personales`)
