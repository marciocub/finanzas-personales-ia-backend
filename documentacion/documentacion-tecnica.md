# Documentación técnica — Gestor de finanzas personales

Guía para instalar, configurar y levantar el proyecto en local y con Docker Desktop.

## 1. Qué es el sistema

Aplicación de **finanzas personales y presupuestos** con:

| Pieza | Tecnología |
|--------|------------|
| Backend | Java 21, Spring Boot 3.3, Spring Data JPA, Spring Security, JWT, MapStruct |
| Frontend | React 18, Vite, Tailwind CSS, Axios, Recharts, Lucide |
| Base de datos | MySQL 8.0 |
| Orquestación | Docker Compose |

Arquitectura **hexagonal**. El código (clases, endpoints, SQL, comentarios) está en **español**. Paquete base del backend: `com.finanzas`.

## 2. Requisitos

### Opción A — Docker (recomendada)

- Docker Desktop activo (Windows, Linux o macOS)
- Al menos 4 GB de RAM asignados a Docker
- Puertos libres en el host: **3000**, **8080**, **3310**

### Opción B — Desarrollo sin contenedores de app

- JDK 21
- Maven 3.9+
- Node.js 20+
- MySQL 8.0 (puede ser solo el contenedor `mysql-db-finanzas-personales`)

## 3. Estructura del repositorio

```text
finanzas-personales-ia/
├── AGENTS.md                       ← reglas del agente
├── docker-compose.yml
├── finanzas-backend/               ← API Spring Boot
│   ├── docs/
│   │   ├── MEMORY.md
│   │   ├── PLAN-ARQUITECTURA.md
│   │   └── SKILLS.md
│   └── documentacion/
│       └── documentacion-tecnica.md    ← este archivo
└── finanzas-frontend/              ← SPA React
```

Backend (capas):

```text
com.finanzas
├── domain/                 Java 21 puro (sin Spring, JPA ni Lombok)
├── application/            puertos, servicios, DTOs
└── infrastructure/         REST, JPA, MapStruct, JWT, configuración
```

## 4. Levantamiento con Docker Desktop

Desde la **raíz del repositorio** (`finanzas-personales-ia`):

```bash
docker compose up --build
```

La primera vez descarga imágenes (MySQL, Maven/JDK, Node, Nginx) y compila backend y frontend. Puede tardar varios minutos.

Servicios que se crean:

| Servicio Compose | Contenedor | Puerto en el host | Rol |
|------------------|------------|-------------------|-----|
| `mysql-db-finanzas-personales` | `mysql-db-finanzas-personales` | **3310** → 3306 | MySQL 8 |
| `backend` | `finanzas-backend` | **8080** | API Spring Boot |
| `frontend` | `finanzas-frontend` | **3000** → 80 | Nginx + SPA |

URLs:

- Frontend: [http://localhost:3000](http://localhost:3000)
- Backend: [http://localhost:8080](http://localhost:8080)
- MySQL: `localhost:3310`

### Comandos habituales

```bash
# Levantar en segundo plano
docker compose up --build -d

# Ver estado
docker compose ps

# Logs
docker compose logs -f
docker compose logs -f backend
docker compose logs -f mysql-db-finanzas-personales

# Detener (conserva el volumen de MySQL)
docker compose down

# Detener y borrar datos de la base
docker compose down -v
```

### Cómo se habla el frontend con el API en Docker

La imagen del frontend se sirve con **Nginx**. Las llamadas a `/api/` se reenvían al contenedor `backend:8080`. Por eso, dentro de Docker, Axios usa `baseURL` vacío (rutas relativas `/api/...`).

`VITE_API_URL` en `docker-compose.yml` **no cambia** el bundle ya compilado: Vite solo lee variables de entorno en tiempo de **build**.

## 5. Credenciales de MySQL (Docker)

Definidas en `docker-compose.yml`:

| Clave | Valor |
|-------|--------|
| Base de datos | `finanzas_personales` |
| Usuario de aplicación | `finanzas` |
| Clave de aplicación | `finanzas` |
| Usuario root | `root` |
| Clave root | `root` |
| Puerto host | `3310` |

El esquema se crea al **inicializar el volumen vacío** con:

`finanzas-backend/src/main/resources/db/script-creacion.sql`

Tablas: `usuarios`, `transacciones`, `presupuestos`.

Si cambiaste el SQL y MySQL ya tenía datos, el script **no se vuelve a ejecutar**. Hay que borrar el volumen (`docker compose down -v`) o aplicar el SQL a mano.

Hibernate usa `ddl-auto: none`: no altera tablas; el origen del esquema es el script SQL.

## 6. JWT y seguridad

| Propiedad | Valor por defecto / Compose |
|-----------|-----------------------------|
| Secreto | `JWT_SECRETO` (mínimo 32 caracteres) |
| Expiración | `JWT_EXPIRACION_MS` = 86400000 (24 h) |

Rutas públicas:

- `POST /api/autenticacion/registro`
- `POST /api/autenticacion/inicio-sesion`

El resto exige cabecera:

```http
Authorization: Bearer <token>
```

El frontend guarda el token en `localStorage` (`tokenFinanzas`) y el interceptor de Axios lo envía en cada request.

## 7. Primer uso (flujo funcional)

1. Abrir [http://localhost:3000](http://localhost:3000).
2. Ir a **Registrate**.
3. Completar correo, clave (mínimo 6 caracteres) y moneda (`ARS`, `USD`, `EUR`, `BRL`).
4. En **Presupuestos**, crear un límite por categoría y fechas.
5. En **Transacciones**, cargar ingresos y gastos. Los filtros (fechas, categoría, tipo) llaman a la API que usa JPA Specifications.
6. En el **Panel**, ver ingresos, gastos, saldo y el gráfico gastos vs límite.

Regla de negocio: un **gasto** de una categoría con presupuesto vigente que **supere el límite** responde **HTTP 409** (`PresupuestoExcedidoException`). Al 80% del límite el dominio genera una alerta (el alta sigue permitida si no se excede el tope).

## 8. API REST

Base: `http://localhost:8080`

| Método | Ruta | Auth | Descripción |
|--------|------|------|-------------|
| POST | `/api/autenticacion/registro` | No | Alta de usuario y JWT |
| POST | `/api/autenticacion/inicio-sesion` | No | JWT |
| GET | `/api/transacciones` | Sí | Listado filtrable |
| POST | `/api/transacciones` | Sí | Alta (valida presupuesto si es gasto) |
| GET | `/api/transacciones/resumen` | Sí | Totales + listado |
| GET | `/api/presupuestos` | Sí | Listado del usuario |
| POST | `/api/presupuestos` | Sí | Alta de presupuesto |

### Registro

```json
POST /api/autenticacion/registro
{
  "correo": "ana@correo.com",
  "clave": "secreto",
  "monedaPrincipal": "ARS"
}
```

### Inicio de sesión

```json
POST /api/autenticacion/inicio-sesion
{
  "correo": "ana@correo.com",
  "clave": "secreto"
}
```

Respuesta típica:

```json
{
  "token": "eyJ...",
  "usuarioId": 1,
  "correo": "ana@correo.com",
  "monedaPrincipal": "ARS"
}
```

### Filtros de transacciones (query params)

| Parámetro | Ejemplo |
|-----------|---------|
| `fechaDesde` | `2026-10-01T00:00:00` |
| `fechaHasta` | `2026-10-31T23:59:59` |
| `categoria` | `ALIMENTACION` |
| `tipo` | `INGRESO` o `GASTO` |
| `montoMinimo` | `100` |
| `montoMaximo` | `5000` |

Enums de categoría: `ALIMENTACION`, `TRANSPORTE`, `VIVIENDA`, `SALUD`, `EDUCACION`, `ENTRETENIMIENTO`, `SERVICIOS`, `AHORRO`, `SUELDO`, `OTROS`.

## 9. Desarrollo local (sin Docker de backend/frontend)

### 9.1 Solo MySQL en Docker

```bash
docker compose up mysql-db-finanzas-personales
```

### 9.2 Backend

Desde `finanzas-backend`:

```bash
mvn test
mvn spring-boot:run
```

El `application.yml` apunta por defecto a `localhost:3310` con usuario `finanzas`.

### 9.3 Frontend

Desde `finanzas-frontend`:

```bash
npm install
npm run dev
```

Vite corre en el puerto **3000** y **proxea** `/api` a `http://localhost:8080` (`vite.config.js`). No hace falta `VITE_API_URL` en desarrollo.

## 10. Problemas frecuentes

| Síntoma | Qué revisar |
|---------|-------------|
| Puerto ocupado | Otro proceso en 3000, 8080 o 3310. Cambiar el mapeo en `docker-compose.yml` o liberar el puerto. |
| Backend no arranca, espera MySQL | El backend espera el *healthcheck* de MySQL. Ver `docker compose logs mysql-db-finanzas-personales`. |
| Tablas inexistentes | El init SQL solo corre en volumen nuevo. `docker compose down -v` y volver a `up`. |
| 401 en el frontend | Token vencido o ausente. Volver a iniciar sesión. |
| CORS en local | El backend permite orígenes `http://localhost:3000` y `http://localhost:5173`. En Docker el proxy Nginx evita CORS. |
| Docker Desktop detenido | Arrancar Docker Desktop y esperar a que el motor esté en “Running”. |
| Compilación Maven lenta | Normal en el primer `--build`; las capas de dependencias se cachean después. |

## 11. Referencias internas

- Plan de arquitectura: `finanzas-backend/docs/PLAN-ARQUITECTURA.md`
- Reglas del agente: `AGENTS.md`
- Plantillas: `finanzas-backend/docs/SKILLS.md`
- Decisiones: `finanzas-backend/docs/MEMORY.md`
- Script SQL: `finanzas-backend/src/main/resources/db/script-creacion.sql`
- Compose: `docker-compose.yml`
