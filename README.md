# spring-commerce

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](#)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](#)
[![Liquibase](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white)](#)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](#)

An e-commerce REST API built with **Java 21** and **Spring Boot 4**.

Designed as a **package-by-feature modular monolith**: each domain module keeps its own presentation, business logic, persistence, and DTO layers, and modules are meant to collaborate through interfaces rather than each other's internals.

## Key Features

- **Product Catalog & Inventory:** Full CRUD operations, filtering by brand or price range, price sorting, and **optimistic locking** (`@Version`) that guards stock updates against lost updates under concurrent writes.
- **Shopping Cart:** Lazy cart creation, stock availability checks, automatic quantity merging, per-request quantity validation (1-1000), and live total calculation without storing prices in the cart table.
- **Order Management:** Atomic checkout in a single transaction (stock deduction, price and product name snapshots, cart clearing), an order status state machine with validated transitions, and automatic stock restoration on cancellation.
- **Authentication & Role-Based Security:** Stateless **JWT** authentication with **Spring Security**, **BCrypt** password hashing, role-based access control (`ADMIN` / `CUSTOMER`), and ownership checks (`CurrentUser`) to prevent IDOR across cart and order endpoints.
- **Centralized Exception Handling:** Unified interception of domain, security, and validation errors, returning structured error responses for the cases it covers.

## Project Structure

```
src/main/java/com/abdalrahman/springcommerce/
├── auth/
│   ├── controller/
│   ├── service/
│   └── dtos/
├── cart/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dtos/
│   ├── Cart.java
│   └── CartItem.java
├── order/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dtos/
│   ├── Order.java
│   ├── OrderItem.java
│   └── OrderStatus.java
├── product/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dtos/
│   └── Product.java
├── user/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dtos/
│   ├── User.java
│   └── Role.java
├── security/
│   ├── CurrentUser.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   ├── RestSecurityErrorHandler.java
│   └── SecurityConfig.java
└── shared/
    ├── errors/
    └── utils/
```

## Design Decisions

- **Cart is mutable, order history is snapshotted.** The cart shows live prices computed from the catalog. Each order item stores `price_at_purchase` and `product_name`, so order history never changes when the catalog does.
- **Atomic checkout.** `placeOrder` runs in one transaction. If any item is out of stock, the whole operation rolls back and the cart and stock are left untouched.
- **Overselling protection.** Stock changes go through `ProductService` and are guarded by `@Version` optimistic locking on `Product`, so a conflicting concurrent update fails instead of silently overwriting.
- **Concurrent order status changes.** `Order` also carries `@Version`, intended to stop two simultaneous transitions (for example, a cancellation and a shipment) from both silently succeeding. This behavior is not yet covered by an automated test.
- **Order state machine.** Allowed transitions: `PENDING` to `CONFIRMED` to `SHIPPED` to `DELIVERED`. Cancellation is allowed only from `PENDING` or `CONFIRMED`, and it restores stock. Status changes, including cancellation, are performed by an admin through the status endpoint; there is no customer self-service cancellation.
- **Stateless JWT & IDOR prevention.** Cart and order endpoints resolve the authenticated user directly from the JWT via `CurrentUser` instead of accepting `userId` in the URL, and order lookups enforce owner-or-admin checks.
- **Module boundaries.** Modules are designed to talk to each other through service interfaces rather than repositories, and Cart and Order reference users and products by id, not by entity. These boundaries are a convention, not enforced by tooling.
- **Database as the last line of defense.** Uniqueness invariants such as one cart per user and one row per product per cart are enforced with database constraints, not only in code.

## Database Schema

![Database ERD](docs/erd.png)

## Getting Started

### Prerequisites

- **JDK 21** or higher
- **Docker** and **Docker Compose**

### 1. Clone the Repository

```bash
git clone https://github.com/abdalrahmanehab/spring-commerce.git
cd spring-commerce
```

### 2. Configure Environment

Copy `.env.example` to `.env` and set a real JWT secret:

```bash
cp .env.example .env
```

`JWT_SECRET` is **required** and has no default; the application will not start without it. Use a strong random value, for example a 256-bit base64 secret:

```bash
openssl rand -base64 32
```

Export `JWT_SECRET` (or load `.env` through your IDE or run configuration) before starting the app.

Database settings are optional for local runs. They default to the Dockerized PostgreSQL instance and can be overridden through environment variables:

- `DB_URL` (default `jdbc:postgresql://localhost:5433/springcommerce`)
- `DB_USERNAME` (default `postgres`)
- `DB_PASSWORD` (default matches the Docker Compose service)

### 3. Start PostgreSQL

```bash
docker compose up -d
```

> **Note:** The PostgreSQL container is mapped to host port `5433` to prevent port collisions with any existing local PostgreSQL instance.

### 4. Run the Application

```bash
bash mvnw spring-boot:run
```

(`bash mvnw` works even if the wrapper is not marked executable; alternatively run `chmod +x mvnw` once and use `./mvnw`. On Windows, use `mvnw.cmd`.)

The `local` profile is active by default. The API will be available at the base URL: `http://localhost:8080/commerce`

### Admin Access for Local Testing

Registration always creates `CUSTOMER` accounts, and there is no seeded admin user. To try the admin endpoints locally, promote a registered user directly in the database:

```bash
docker exec -it springcommerce-db psql -U postgres -d springcommerce \
  -c "UPDATE users SET role = 'ADMIN' WHERE email = 'you@example.com';"
```

## Demo Flow

A quick end-to-end path through the API (all URLs are relative to `http://localhost:8080/commerce`):

1. **Register** a customer: `POST /api/v1/auth/register`
2. **Log in**: `POST /api/v1/auth/login` and keep the returned JWT. Pass it as `Authorization: Bearer <token>` on every protected call.
3. **Create a product** (admin account, see above): `POST /api/v1/products`
4. **Add it to the cart**: `POST /api/v1/carts/items`
5. **Check out**: `POST /api/v1/orders` creates the order, deducts stock, and clears the cart in one transaction.
6. **Cancel the order** (admin): `PATCH /api/v1/orders/{orderId}/status` with `CANCELLED` restores the stock.
7. **Verify**: `GET /api/v1/products/id/{id}` shows the restored quantity, and `GET /api/v1/orders` shows the cancelled order with its original price snapshots.

## Running Tests

```bash
bash mvnw test
```

The current suite is a Spring Boot context-load smoke test, so it needs the same prerequisites as running the app: PostgreSQL up (`docker compose up -d`) and `JWT_SECRET` set in the environment. Broader integration and concurrency tests are on the roadmap below.

## API Testing

This repository includes a ready-to-use Postman collection.

1. Import `postman/spring-commerce.postman_collection.json` into Postman.
2. The collection defines its own **collection variables** (`baseUrl`, `token`, `customerToken`, `adminToken`, `userId`, `productId`, `orderId`). Set `baseUrl` to `http://localhost:8080/commerce`.
3. Register or log in via `/api/v1/auth/login` and paste the returned JWT into the `token` variable (or use `customerToken` / `adminToken` to keep roles separate). Protected requests send it as a `Bearer` token in the `Authorization` header.

## API Endpoints

### Auth Module

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | Public | Register a new user account (always `CUSTOMER`) |
| `POST` | `/api/v1/auth/login` | Public | Authenticate and receive a JWT token |

### User Module

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/users/me` | Authenticated | Retrieve the current authenticated user's profile |
| `GET` | `/api/v1/users` | Admin | Retrieve all users |
| `GET` | `/api/v1/users/{id}` | Admin | Retrieve a user by ID |
| `DELETE` | `/api/v1/users/{id}` | Admin | Delete a user by ID |

### Product Module

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/products` | Admin | Create a new product |
| `GET` | `/api/v1/products` | Public | Retrieve all products |
| `GET` | `/api/v1/products/id/{id}` | Public | Retrieve a product by ID |
| `GET` | `/api/v1/products/sku/{sku}` | Public | Retrieve a product by SKU |
| `GET` | `/api/v1/products/brand/{brand}` | Public | Filter products by brand |
| `GET` | `/api/v1/products/price-range?min={min}&max={max}` | Public | Filter products within a price range |
| `GET` | `/api/v1/products/sorted?direction={ASC\|DESC}` | Public | Sort all products by price |
| `PUT` | `/api/v1/products/{id}` | Admin | Update an existing product |
| `DELETE` | `/api/v1/products/{id}` | Admin | Delete a product by ID |
| `DELETE` | `/api/v1/products` | Admin | Delete all products |

### Cart Module

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/carts` | Authenticated | Retrieve the current user's cart |
| `POST` | `/api/v1/carts/items` | Authenticated | Add an item to the cart (quantity 1-1000 per request) |
| `PUT` | `/api/v1/carts/items/{productId}` | Authenticated | Update item quantity in the cart |
| `DELETE` | `/api/v1/carts/items/{productId}` | Authenticated | Remove an item from the cart |
| `DELETE` | `/api/v1/carts` | Authenticated | Clear all items from the cart |

### Order Module

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/orders` | Authenticated | Place an order from the current user's cart |
| `GET` | `/api/v1/orders` | Authenticated | Retrieve the current user's order history |
| `GET` | `/api/v1/orders/{orderId}` | Owner / Admin | Retrieve an order by ID |
| `GET` | `/api/v1/orders/user/{userId}` | Admin | Retrieve orders for a specific user |
| `PATCH` | `/api/v1/orders/{orderId}/status` | Admin | Update order status (including cancellation) |

## Profiles

Activate a specific profile via `-Dspring-boot.run.profiles=<profile>` or `SPRING_PROFILES_ACTIVE`:

- **`local`** - Default profile. Connects to the Dockerized PostgreSQL instance (`localhost:5433`).
- **`dev`**, **`prod`**, **`test`** - Currently contain logging/environment overrides only; all modules use Spring Data JPA with PostgreSQL.


## Roadmap

- [x] Product, User, Cart, and Order modules
- [x] JWT authentication and role-based authorization
- [x] Optimistic locking on products and orders, input validation hardening, externalized DB credentials
- [ ] Idempotent checkout, so client retries or double-clicks cannot create duplicate orders or double-deduct stock
- [ ] Security integration tests (role enforcement and IDOR checks)
- [ ] Product pagination and OpenAPI documentation
- [ ] Integration tests with Testcontainers and a concurrency test for overselling
- [ ] CI pipeline with GitHub Actions
- [ ] Semantic product search with Spring AI (deferred; not a current priority)
