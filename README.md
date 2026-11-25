# Restaurant API

API REST desarrollada con Spring Boot para la gestión integral de un sistema de restaurante. Permite administrar mesas, platillos, pedidos y sus detalles, con persistencia en PostgreSQL, arquitectura por capas, documentación interactiva con Swagger y seguridad mediante Spring Security con autenticación JWT.

## Tecnologías utilizadas

| Tecnología | Propósito |
|------------|-----------|
| Java 21 | Lenguaje de programación |
| Spring Boot 3.4.1 | Framework principal |
| Spring Web | Exposición de servicios REST |
| Spring Data JPA | Capa de persistencia y repositorios |
| Spring Security | Autenticación y autorización |
| JWT (jjwt 0.12.6) | Generación y validación de tokens |
| PostgreSQL | Base de datos relacional |
| Hibernate | ORM y mapeo objeto-relacional |
| DTOs | Transferencia de datos entre solicitudes y respuestas de la API |
| Swagger OpenAPI (springdoc 2.8.6) | Documentación interactiva de la API |
| Gradle | Herramienta de construcción |

## Arquitectura del proyecto

El proyecto sigue una arquitectura por capas, separando responsabilidades de forma clara:

```
controller   — Capa de presentación. Expone los endpoints REST y recibe las peticiones HTTP.
service      — Capa de negocio. Contiene la lógica de la aplicación y orquesta las operaciones.
repository   — Capa de persistencia. Acceso a la base de datos mediante Spring Data JPA.
entity       — Modelo de datos. Clases que representan las tablas de la base de datos.
auth         — Controlador y servicio de autenticación. Login y generación de tokens JWT.
security     — Configuración de seguridad. Filtro JWT, utilería de tokens y UserDetailsService.
config       — Configuraciones adicionales. Seguridad HTTP, Swagger/OpenAPI, CORS.
```

## Diagrama de arquitectura

```mermaid
flowchart TD

    Cliente[Cliente / Swagger UI]

    Security[Security Filter Chain<br/>JWT + BCrypt]

    Controller[Controllers REST]

    Service[Services<br/>Lógica de negocio]

    Repository[Repositories<br/>Spring Data JPA]

    Entity[Entities<br/>JPA Hibernate]

    DB[(PostgreSQL)]

    Cliente --> Security

    Security --> Controller

    Controller --> Service

    Service --> Repository

    Repository --> Entity

    Entity --> DB
```

El sistema utiliza una arquitectura por capas donde los controladores reciben solicitudes HTTP, los servicios contienen la lógica de negocio, los repositorios gestionan la persistencia mediante JPA/Hibernate y PostgreSQL almacena la información. La seguridad se maneja mediante Spring Security utilizando autenticación JWT.

## Funcionalidades implementadas

### Mesas
- CRUD completo de mesas.
- Consulta por número de mesa.

### Platillos
- CRUD completo de platillos.
- Consulta por categoría.

### Pedidos
- CRUD completo de pedidos.
- Consulta por estado (pendiente, preparación, listo, entregado, pagado).
- Creación de pedidos completos con detalles mediante DTO.
- Persistencia automática de DetallePedido utilizando CascadeType.ALL.
- La relación maestro-detalle permite guardar un Pedido y sus Detalles asociados en una sola operación.

### Detalles de pedido
- Gestión de cantidades y precio unitario.
- Cálculo de subtotales por detalle.
- Relación maestro-detalle con pedidos.

## Seguridad JWT

El flujo de autenticación funciona de la siguiente manera:

1. El usuario envía sus credenciales mediante `POST /auth/login`.
2. Spring Security (`DaoAuthenticationProvider`) valida el usuario y la contraseña.
3. `PasswordEncoder` (BCrypt) compara la contraseña ingresada contra el hash almacenado en la base de datos.
4. Si las credenciales son correctas, `JwtUtil` genera un token JWT firmado con HS256. La clave secreta debe cumplir la longitud mínima de HS256 (32 bytes); si no, JJWT rechaza la clave y el arranque falla.
5. En peticiones posteriores, `JwtFilter` intercepta los endpoints protegidos, extrae el token del header `Authorization` y lo valida.
6. Si el token es válido, `SecurityContextHolder` registra la autenticación y permite el acceso al recurso.

Firebase Admin está incluido como infraestructura opcional y desactivada por defecto (`firebase.enabled=false`). No participa en este flujo: la integración de Firebase Authentication con el JWT de Spring (C2) está pendiente.

### Ejemplo de autenticación

**Petición:**

`POST /auth/login`

```json
{
    "username": "admin",
    "password": "1234"
}
```

**Respuesta:**

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Uso del token

Para acceder a los endpoints protegidos, el token debe enviarse en el header de la siguiente forma:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Usuario de prueba

| Campo | Valor |
| --- | --- |
| Usuario | `admin` |
| Password | `1234` |
| Rol | `ADMIN` |

La contraseña está almacenada en la base de datos mediante BCrypt.

## Endpoints principales

### Auth

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/login` | Inicia sesión y devuelve un token JWT |

### Mesas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/mesas` | Obtener todas las mesas |
| POST | `/api/mesas` | Crear una nueva mesa |
| GET | `/api/mesas/{id}` | Obtener mesa por ID |
| DELETE | `/api/mesas/{id}` | Eliminar mesa por ID |

### Platillos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/platillos` | Obtener todos los platillos |
| POST | `/api/platillos` | Crear un nuevo platillo |
| GET | `/api/platillos/{id}` | Obtener platillo por ID |
| GET | `/api/platillos/categoria/{categoria}` | Obtener platillos por categoría |
| DELETE | `/api/platillos/{id}` | Eliminar platillo por ID |

### Pedidos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pedidos` | Obtener todos los pedidos |
| POST | `/api/pedidos` | Crear un nuevo pedido |
| GET | `/api/pedidos/{id}` | Obtener pedido por ID |
| GET | `/api/pedidos/estado/{estado}` | Obtener pedidos por estado |
| DELETE | `/api/pedidos/{id}` | Eliminar pedido por ID |
| POST | `/api/pedidos/con-detalles` | Crear un pedido junto con sus detalles utilizando CascadeType.ALL |

### Detalles de pedido

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/detalles-pedido` | Obtener todos los detalles de pedidos |
| POST | `/api/detalles-pedido` | Crear un detalle de pedido |
| GET | `/api/detalles-pedido/{id}` | Obtener detalle de pedido por ID |
| DELETE | `/api/detalles-pedido/{id}` | Eliminar detalle de pedido por ID |

## Swagger

La documentación interactiva de la API está disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

Swagger permite:
- Explorar y probar todos los endpoints disponibles.
- Obtener un token JWT mediante el endpoint de login.
- Autorizar las solicitudes usando el botón **Authorize** ingresando únicamente el token JWT. Swagger agrega automáticamente el prefijo Bearer en las solicitudes HTTP.
- Visualizar los esquemas de petición y respuesta de cada operación.

## Configuración y ejecución

### Requisitos

- Java 21.
- PostgreSQL instalado y en ejecución.
- Gradle (incluye wrapper `gradlew`).

### Variables de entorno

Las credenciales y el secreto JWT **no se incluyen en el archivo de configuración versionado**. La aplicación los resuelve desde variables de entorno:

| Variable | Obligatoria | Descripción |
|----------|-------------|-------------|
| `DB_URL` | No | URL JDBC. Por defecto `jdbc:postgresql://localhost:5432/restaurant_db`. |
| `DB_USERNAME` | Sí | Usuario de PostgreSQL. Debe definirse explícitamente; se recomienda el usuario dedicado `restaurant_app`. |
| `DB_PASSWORD` | Sí | Contraseña de PostgreSQL. |
| `JWT_SECRET` | Sí | Secreto de firma HS256. Debe tener al menos 32 bytes (256 bits). |
| `JWT_EXPIRATION` | No | Vigencia del token en milisegundos. Por defecto `86400000` (24 h). |

`DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET` son obligatorias. La aplicación valida `DB_USERNAME` y `DB_PASSWORD` al inicio y **no arranca** si faltan o están vacías. `JWT_SECRET` también debe estar definido y cumplir la longitud mínima de HS256; de lo contrario el arranque falla.

> Si estos valores se versionaron en el pasado, es obligatorio rotarlos: eliminar el archivo del commit actual no borra el historial de Git.

> Spring Boot no carga archivos `.env` de forma nativa. Si usas una herramienta externa que lo haga, el archivo está ignorado por Git.

Ejemplo en PowerShell:

```powershell
$env:DB_USERNAME = "restaurant_app"
$env:DB_PASSWORD = "<contraseña>"
$env:JWT_SECRET  = "<secreto-de-64-caracteres>"
gradlew bootRun
```

Generación de un secreto JWT (PowerShell 7):

```powershell
$b = New-Object byte[] 48
[System.Security.Cryptography.RandomNumberGenerator]::Fill($b)
[Convert]::ToBase64String($b)
```

> No se debe reutilizar el secreto anterior ni compartirlo. Al rotarlo, todos los tokens emitidos dejan de ser válidos y los clientes deben iniciar sesión de nuevo.

### Base de datos

Crear una base de datos llamada `restaurant_db` en PostgreSQL. El usuario dedicado recomendado es `restaurant_app`:

```sql
CREATE ROLE restaurant_app LOGIN PASSWORD '<contraseña>';
GRANT CONNECT ON DATABASE restaurant_db TO restaurant_app;

\c restaurant_db
GRANT USAGE, CREATE ON SCHEMA public TO restaurant_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO restaurant_app;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO restaurant_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO restaurant_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT USAGE, SELECT ON SEQUENCES TO restaurant_app;
```

Las tablas se crean automáticamente gracias a `ddl-auto: update`. Ese modo puede requerir además la propiedad de las tablas existentes o privilegios DDL equivalentes. La opción más simple es que `restaurant_app` sea propietario de la base (`ALTER DATABASE restaurant_db OWNER TO restaurant_app;`, requiere superusuario) o usar un usuario de migración con privilegios DDL.

### Configuración local

No se versiona ningún archivo con credenciales. La opción recomendada es definir las variables de entorno en el IDE o en la terminal. Si necesitas un archivo local, usa `src/main/resources/application-local.yml` (o `.yaml`), ignorado por Git, actívalo con `SPRING_PROFILES_ACTIVE=local` y declara `DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET` como claves de nivel superior.

### Ejecución

**Windows:**

```bash
gradlew bootRun
```

**Linux / Mac:**

```bash
./gradlew bootRun
```

La aplicación se iniciará en `http://localhost:8080`.

## Contrato de errores

Todas las respuestas de error usan el mismo formato `ErrorResponse`:

```json
{
  "timestamp": "2026-11-24T10:15:30",
  "status": 404,
  "error": "Not Found",
  "message": "Platillo no encontrado",
  "path": "/api/platillos/999"
}
```

Cuando A1 esté implementado, los errores de validación añadirán el arreglo `details`:

```json
"details": [{ "field": "precio", "issue": "debe ser mayor que 0" }]
```

Este formato se aplica también a los errores 401 y 403 generados por Spring Security. Los 401 de recursos protegidos incluyen la cabecera `WWW-Authenticate: Bearer`; los 405 incluyen `Allow` con los métodos soportados.

### Códigos de respuesta HTTP

| Código | Cuándo se devuelve |
|--------|--------------------|
| 200 OK | Petición exitosa |
| 201 Created | Recurso creado correctamente |
| 204 No Content | Petición exitosa sin contenido en la respuesta |
| 400 Bad Request | JSON malformado o tipo de dato inválido. La validación de campos con `details` se activará en A1. |
| 401 Unauthorized | Credenciales inválidas o token ausente, inválido o expirado. En recursos protegidos incluye `WWW-Authenticate: Bearer`. |
| 403 Forbidden | Autenticado sin permisos. La autorización por roles se implementará en A4; no implica Firebase activo. |
| 404 Not Found | Recurso inexistente o ruta no encontrada. |
| 405 Method Not Allowed | Método HTTP no soportado; incluye la cabecera `Allow`. |
| 409 Conflict | Violación de integridad de datos (clave foránea, valor único, etc.). La distinción fina con 400 se completará en A1. |
| 415 Unsupported Media Type | `Content-Type` no soportado por el recurso. |
| 500 Internal Server Error | Error no controlado o fallo de infraestructura (sin detalles internos en la respuesta). |

### Estado de implementación

- **A1 (validación de entrada):** pendiente. El handler de 400 con `details` está preparado pero no se activa todavía.
- **A4 (autorización por roles):** pendiente. El 403 existe como contrato, pero no hay reglas por rol.
- **A5 (pruebas automatizadas):** pendiente. La evidencia existente es manual y previa a este incremento.
- **C2 (Firebase Authentication → Spring JWT):** pendiente. Firebase Admin está incorporado y desactivado por defecto (`firebase.enabled=false`), pero no hay endpoint que intercambie un ID Token por un JWT ni aprovisionamiento de usuarios.

### Compatibilidad con clientes Flutter

- Los payloads exitosos no cambian.
- El cliente debe usar el código HTTP como señal principal y leer `message` del JSON.
- Debe existir un *fallback* si el cuerpo no es JSON (por ejemplo, errores de infraestructura).
- Los errores 401 de seguridad usan `ErrorResponse`; antes devolvían `{ "error", "mensaje" }`.
- La verificación contra los modelos del cliente Flutter queda pendiente. Este incremento no modifica Flutter.

## Evidencia de pruebas

> La evidencia siguiente es manual y previa a este incremento. El contrato nuevo (404 de rutas, 415, cabeceras y validación de secretos) queda pendiente de VALIDATE. No hay pruebas automatizadas nuevas; A5 está pendiente.

- `POST /auth/login` con credenciales válidas genera un token JWT correctamente.
- `GET /api/platillos` sin token responde `401 Unauthorized`.
- `GET /api/platillos` con token JWT válido responde `200 OK` con la lista de platillos.
- Swagger UI permite autorizar y probar todos los endpoints protegidos correctamente.
- POST /api/pedidos/con-detalles crea correctamente un Pedido y sus DetallePedido asociados.
- La persistencia Cascade fue comprobada verificando la creación automática de registros relacionados en PostgreSQL.

## Autor

**David Morales Guerrero**

Proyecto universitario de Ingeniería de Software — Programación Aplicada.
