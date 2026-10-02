# spring-commerce

An e-commerce REST API built with Java 21 and Spring Boot.

Package-by-feature modular monolith. Each feature owns its controller, service, repository, and DTOs. Dependency Inversion via interfaces.

## Architecture

    src/main/java/com/abdalrahman/springcommerce/
    ├── product/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   └── dtos/
    └── shared/
        ├── errors/
        └── utils/

## Tech Stack

- Language: Java 21
- Framework: Spring Boot 3
- Persistence: In-memory (ConcurrentHashMap) -> JPA + PostgreSQL planned
- Validation: Bean Validation
- Build: Maven

## Features

- Product module: CRUD, filtering by brand and price range, sorting
- Centralized exception handling with consistent error envelope
- Bean Validation with messages in validations.properties
- Thread-safe in-memory store (ConcurrentHashMap + AtomicLong)

## Getting Started

    git clone https://github.com/abdalrahmanehab/spring-commerce.git
    cd spring-commerce
    ./mvnw spring-boot:run

Base URL: http://localhost:8080/commerce

## API Endpoints

### Product Module

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/v1/products | Create a product |
| GET | /api/v1/products | Get all products |
| GET | /api/v1/products/id/{id} | Get product by ID |
| GET | /api/v1/products/sku/{sku} | Get product by SKU |
| GET | /api/v1/products/brand/{brand} | Filter products by brand |
| GET | /api/v1/products/price-range?min=&max= | Filter products by price range |
| GET | /api/v1/products/sorted?direction= | Sort products by price (ASC/DESC) |
| PUT | /api/v1/products/{id} | Update a product |
| DELETE | /api/v1/products/{id} | Delete a product |
| DELETE | /api/v1/products | Delete all products |

## Roadmap

- [x] Product module
- [ ] Spring Data JPA + PostgreSQL
- [ ] User module + Spring Security + JWT
- [ ] Cart module
- [ ] Order module
- [ ] Docker + GitHub Actions

## Profiles

dev (default), local, test, prod