# Restaurant API - Architecture

## General Description

Restaurant API is a RESTful web service built with Spring Boot for managing restaurant tables, customer orders, order items, and menu dishes. The project follows a layered architecture pattern to keep concerns separated, improve maintainability, and allow the application to evolve through multiple delivery stages.

## Technologies

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Programming language |
| Spring Boot | 3.4.1 | Application framework |
| Spring Data JPA | - | Database access and ORM |
| Hibernate | - | JPA implementation |
| PostgreSQL | - | Relational database |
| Gradle | 8.11.1 | Build tool and dependency management |
| MapStruct | 1.6.3 | Object mapping between layers |
| SpringDoc OpenAPI | - | API documentation (Swagger) |
| Git | - | Version control |

## Package Structure

```
com.david.restaurantapi
├── config/
├── controller/
├── domain/
├── entity/
├── exception/
├── mapper/
├── repository/
├── service/
└── RestaurantApiApplication.java
```

## Layer Responsibilities

### config
Contains configuration classes for the application. This includes bean definitions, CORS configuration, and any custom Spring configuration required by the project.

### controller
Exposes RESTful endpoints to the client. Each controller receives HTTP requests, invokes the corresponding service, and returns HTTP responses using `ResponseEntity`. Controllers are responsible for request validation and response formatting.

### domain
Contains Plain Old Java Objects (POJOs) that represent the business model. These classes are independent of any persistence technology and contain no JPA annotations. The domain layer is the core of the business logic and is decoupled from the database.

### entity
Contains JPA entities that map directly to database tables. Each entity uses annotations such as `@Entity`, `@Table`, `@Column`, `@Id`, `@GeneratedValue`, `@OneToMany`, and `@ManyToOne`. This layer is tightly coupled to Hibernate and PostgreSQL.

### exception
Contains custom exception classes and a global exception handler (`@RestControllerAdvice`) that catches exceptions thrown by any layer and returns consistent HTTP error responses. This prevents HTTP 500 errors from reaching the client.

### mapper
Contains MapStruct interfaces that convert objects between the entity layer and the domain layer. Each mapper defines `toDomain()` and `toEntity()` methods. Mappers use `@Mapper(componentModel = "spring")` to be automatically registered as Spring beans.

### repository
Contains Spring Data `CrudRepository` interfaces that provide standard CRUD operations for each entity. Spring Data JPA automatically implements these interfaces at runtime.

### service
Contains business logic classes annotated with `@Service`. Services orchestrate operations between controllers and repositories, apply business rules, and use mappers to convert between entities and domain objects.

## Data Flow

```
Client (HTTP Request)
    │
    ▼
controller ──▶ service ──▶ mapper ──▶ repository ──▶ Database
    ▲              │           │            │
    │              │           │            │
    └──────────────┴───────────┴────────────┘
        (Response flows back through layers)
```

1. The client sends an HTTP request to a controller endpoint.
2. The controller delegates to the corresponding service.
3. The service applies business logic and uses a mapper to convert domain objects to entities.
4. The repository persists or retrieves data from the database.
5. The result flows back through the same layers in reverse order.

## Project Rules

- All class names, variables, methods, packages, and commits must be written in English.
- Integer identifiers only (`@Id private Integer id`).
- No Lombok, no Records, no Spring Security.
- No DTOs unless explicitly requested in later deliveries.
- No unnecessary abstraction or features outside the current delivery scope.
- Use `ResponseEntity` for all HTTP responses.
- Avoid HTTP 500 errors by handling exceptions globally.

## Commit Convention

```
feat: implement persistence layer with JPA entities
feat: add domain models using MapStruct
feat: implement service layer
docs: configure Swagger documentation
fix: resolve entity relationship mapping
```

Follow conventional commits: `type: short description`.

## Development Philosophy

- Keep the project simple but professional.
- Prioritize readability and clean architecture.
- Every decision must be compatible with future deliveries.
- Code must be easy to explain during an oral presentation.
- Do not add complexity that is not required by the assignment.