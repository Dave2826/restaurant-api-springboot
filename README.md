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

### Inicio de sesión con Firebase

Cuando `firebase.enabled=true` y Google Application Default Credentials (ADC) están disponibles, Flutter obtiene un Firebase ID Token y lo canjea por el JWT propio del backend. El cliente **nunca envía identidad ni rol**: Spring Boot verifica el token con Firebase Admin y decide el aprovisionamiento y los permisos.

**Flujo de extremo a extremo verificado:**

```mermaid
flowchart LR
    A[Flutter] --> B[Firebase Authentication]
    B --> C[Firebase ID Token]
    C --> D[POST /auth/firebase]
    D --> E[Spring Boot + Firebase Admin]
    E --> F[Verificación de token y política institucional]
    F --> G[Usuario en PostgreSQL]
    G --> H[JWT propio del backend]
    H --> I[GET /api/platillos]
    I --> J[Catálogo PostgreSQL]
```

1. Flutter registra/inicia sesión mediante Firebase Authentication y obtiene un ID Token.
2. Flutter envía ese token a `POST /auth/firebase`.
3. Firebase Admin valida la firma, vigencia y revocación (`verifyIdToken(idToken, true)`).
4. El backend aplica la política de dominio institucional (`tecdesoftware.edu.mx`) y, si `FIREBASE_REQUIRE_VERIFIED_EMAIL=true`, exige el correo verificado.
5. Si el UID no existe en `users`, se aprovisiona como `username` con rol `USER` y una contraseña aleatoria inutilizable. Si ya existe, se reutiliza.
6. `JwtUtil` emite el JWT HS256 propio, que es la credencial para `/api/**`.
7. Flutter consume `GET /api/platillos` con `Authorization: Bearer <JWT del backend>`; el catálogo se obtiene de PostgreSQL.

El flujo real Flutter → Firebase Authentication → ID Token → Spring Boot/Firebase Admin → aprovisionamiento PostgreSQL → JWT del backend → catálogo protegido fue comprobado manualmente con credenciales reales. La consulta del catálogo mostró 15 platillos.

**Política de correo verificado:** la configuración versionada exige verificación por defecto (`FIREBASE_REQUIRE_VERIFIED_EMAIL=true`). Para la comprobación end-to-end actual se desactivó temporalmente mediante la configuración local; no se debe tomar ese ajuste temporal como política de despliegue. En instalaciones y uso normal, mantén la exigencia activada.

**Petición:**

`POST /auth/firebase`

```json
{
    "idToken": "eyJhbGciOiJSUzI1NiIsImtpZCI6..."
}
```

**Respuesta:** la misma forma que `/auth/login`.

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

| Código | Significado |
|--------|-------------|
| 200 | ID Token válido; se devuelve el JWT del backend. |
| 401 | ID Token ausente, inválido, expirado o revocado. |
| 403 | Dominio no permitido o correo sin verificar cuando la política está activa. |
| 503 | Integración Firebase deshabilitada. |

Si Firebase se habilita pero ADC no está disponible o es inválido, la inicialización de Firebase Admin puede impedir que la aplicación arranque; no es necesariamente una respuesta HTTP 503.

> El cliente debe enviar `Accept: application/json`. El proyecto registra además un conversor XML (por Springdoc), por lo que una petición con `Accept: */*` puede recibir XML.



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
| POST | `/auth/firebase` | Canjea un ID Token de Firebase por un JWT (requiere `firebase.enabled=true`) |

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

### Base de datos

Crear una base de datos llamada `restaurant_db` en PostgreSQL. Para instalaciones nuevas, el usuario dedicado recomendado es `restaurant_app`:

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

`deey1` es el usuario de PostgreSQL del entorno local actual; no es el nombre recomendado para instalaciones nuevas ni debe copiarse como valor predeterminado.

### Configuración local

No se versiona ningún archivo con credenciales. La opción recomendada es definir las variables de entorno en el IDE o en la terminal. Si necesitas un archivo local, usa `src/main/resources/application-local.yml` (o `application-local.yaml`), ignorado por Git, actívalo con `SPRING_PROFILES_ACTIVE=local` y declara `DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET` como claves de nivel superior.

| Variable | Uso | Valor por defecto/documentación |
|---|---|---|
| `DB_URL` | URL JDBC de PostgreSQL | `jdbc:postgresql://localhost:5432/restaurant_db` |
| `DB_USERNAME` / `DB_PASSWORD` | Credenciales de PostgreSQL | Obligatorias |
| `JWT_SECRET` | Clave para JWT HS256 | Obligatoria; mínimo 32 bytes |
| `JWT_EXPIRATION` | Duración del JWT en ms | `86400000` (24 h) |
| `FIREBASE_ENABLED` | Habilita Firebase Admin | `false` |
| `FIREBASE_PROJECT_ID` | Proyecto Firebase | `flutter-spring-test-73f45` |
| `FIREBASE_ALLOWED_DOMAIN` | Dominio institucional | `tecdesoftware.edu.mx` |
| `FIREBASE_REQUIRE_VERIFIED_EMAIL` | Exige correo verificado | `true` |
| `GOOGLE_APPLICATION_CREDENTIALS` | Ruta al JSON de Service Account para ADC | Se define localmente, fuera del repositorio |

Para habilitar Firebase localmente, define `FIREBASE_ENABLED=true` y proporciona ADC mediante `GOOGLE_APPLICATION_CREDENTIALS`, apuntando a un archivo de Service Account guardado fuera del repositorio y de la aplicación. No copies ni subas ese archivo, su contenido, tokens o claves al repositorio.

Ejemplo de variables Firebase en PowerShell (ajusta la ruta segura del archivo):

```powershell
$env:FIREBASE_ENABLED = "true"
$env:FIREBASE_PROJECT_ID = "flutter-spring-test-73f45"
$env:FIREBASE_ALLOWED_DOMAIN = "tecdesoftware.edu.mx"
$env:FIREBASE_REQUIRE_VERIFIED_EMAIL = "true"
$env:GOOGLE_APPLICATION_CREDENTIALS = "C:\ruta-segura\firebase-service-account.json"
```

La verificación del correo se mantuvo temporalmente desactivada durante la prueba end-to-end actual. Para reproducir esa prueba con un correo aún no verificado, el override local puede ser `FIREBASE_REQUIRE_VERIFIED_EMAIL=false`; restáuralo a `true` para el uso normal y no versionices ese override.

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

## Estado de pendientes A1, A4 y A5

Estos puntos de la revisión de API siguen pendientes; la integración Firebase (C2) es una funcionalidad implementada y verificada, no un pendiente de este listado.

- **A1 — Validación de entradas y contrato de errores detallados:** pendiente. El arreglo `details` mostrado a continuación es el contrato previsto; aún no debe considerarse implementado.
- **A4 — Autorización por roles:** pendiente. Actualmente `/api/**` requiere autenticación, pero la configuración no restringe los endpoints por roles específicos. El aprovisionamiento Firebase asigna `USER` en el servidor.
- **A5 — Cobertura automatizada general:** pendiente. Hay pruebas unitarias de Firebase (verificador, servicio y controlador), pero no sustituyen la cobertura automatizada más amplia de reglas de negocio, endpoints ni una prueba end-to-end automatizada.

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
| 401 Unauthorized | No autenticado o token inválido |
| 403 Forbidden | Autenticado pero sin permisos para el recurso |
| 404 Not Found | Recurso no encontrado |

## Evidencia de pruebas

- **Flujo Firebase end-to-end manual verificado:** Flutter → Firebase Authentication → ID Token → `POST /auth/firebase` → Firebase Admin (validación de token y revocación) → aprovisionamiento/reutilización del usuario en PostgreSQL → JWT del backend → `GET /api/platillos` protegido. El catálogo respondió con 15 platillos.
- La prueba end-to-end se ejecutó con la exigencia de correo verificado temporalmente desactivada; la configuración versionada mantiene `FIREBASE_REQUIRE_VERIFIED_EMAIL=true` por defecto.
- **Pruebas unitarias Firebase existentes:** cubren política de dominio, verificación/revocación solicitada, rechazo de tokens, servicio de aprovisionamiento y contrato del controlador. Utilizan mocks y no prueban la verificación criptográfica real ni automatizan el flujo end-to-end.
- `POST /auth/firebase` con `firebase.enabled=false` responde `503` con un mensaje controlado.
- `GET /api/platillos` sin token responde `401 Unauthorized`.
- `GET /api/platillos` con el JWT propio válido responde `200 OK` con el catálogo.
- Swagger UI permite autorizar y probar los endpoints protegidos.
- `POST /api/pedidos/con-detalles` crea correctamente un Pedido y sus DetallePedido asociados.
- La persistencia Cascade fue comprobada verificando la creación automática de registros relacionados en PostgreSQL.



## Autor

**David Morales Guerrero**

Proyecto universitario de Ingeniería de Software — Programación Aplicada.
