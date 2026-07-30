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
4. Si las credenciales son correctas, `JwtUtil` genera un token JWT firmado con HS256. La clave secreta utilizada para firmar los tokens cumple con la longitud mínima requerida por el algoritmo HS256.
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

### Base de datos

Crear una base de datos llamada `restaurant_db` en PostgreSQL. Las credenciales por defecto son:

```
Host: localhost
Puerto: 5432
Base de datos: restaurant_db
Usuario: postgres
Password: definida en application.yml
```

Las tablas se crean automáticamente gracias a `ddl-auto: update`.

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

## Códigos de respuesta HTTP

| Código | Descripción |
|--------|-------------|
| 200 OK | Petición exitosa |
| 201 Created | Recurso creado correctamente |
| 204 No Content | Petición exitosa sin contenido en la respuesta |
| 401 Unauthorized | No autenticado o token inválido |
| 403 Forbidden | Autenticado pero sin permisos para el recurso |
| 404 Not Found | Recurso no encontrado |

## Evidencia de pruebas

- `POST /auth/login` con credenciales válidas genera un token JWT correctamente.
- `GET /api/platillos` sin token responde `401 Unauthorized`.
- `GET /api/platillos` con token JWT válido responde `200 OK` con la lista de platillos.
- Swagger UI permite autorizar y probar todos los endpoints protegidos correctamente.
- POST /api/pedidos/con-detalles crea correctamente un Pedido y sus DetallePedido asociados.
- La persistencia Cascade fue comprobada verificando la creación automática de registros relacionados en PostgreSQL.

## Autor

**David Morales Guerrero**

Proyecto universitario de Ingeniería de Software — Programación Aplicada.
