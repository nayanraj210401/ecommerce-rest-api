# Ecommerce Spring REST API

This document provides an overview of the REST API services available in the E-commerce application. The API is designed to handle various operations related to orders, authentication, payments, and products.

## API Services

### AuthService
- **Description**: Handles user authentication and registration processes.
- **Endpoints**:
  - `POST /auth/register`: Register a new user.
  - `POST /auth/login`: Authenticate a user and generate a JWT token.

### PaymentService
- **Description**: Manages payment processing and related operations.
- **Endpoints**:
  - `POST /payments`: Process a payment.
  - `GET /payments/{transactionId}`: Retrieve payment details by transaction ID.
  - `POST /payments/{transactionId}/refund`: Process a refund for a payment.

### ProductService
- **Description**: Manages operations related to products.
- **Endpoints**:
  - `GET /products`: Retrieve all products.
  - `GET /products/{id}`: Fetch a specific product by its ID.
  - `POST /products`: Create a new product.
  - `PUT /products/{id}`: Update an existing product.
  - `DELETE /products/{id}`: Delete a product by its ID.

## Controllers

### AuthController
- **Description**: Manages HTTP requests for user authentication and registration.
- **Endpoints**:
  - `POST /api/auth/register`: Register a new user.
  - `POST /api/auth/login`: Authenticate a user and generate a JWT token.

### PaymentController
- **Description**: Handles HTTP requests for payment processing.
- **Endpoints**:
  - `POST /api/payments`: Process a payment.
  - `GET /api/payments/{transactionId}`: Retrieve payment details by transaction ID.
  - `POST /api/payments/{transactionId}/refund`: Process a refund for a payment.

### ProductController
- **Description**: Manages HTTP requests for product-related operations.
- **Endpoints**:
  - `GET /api/products`: Retrieve all products.
  - `GET /api/products/{id}`: Fetch a specific product by its ID.
  - `POST /api/products`: Create a new product.
  - `PUT /api/products/{id}`: Update an existing product.
  - `DELETE /api/products/{id}`: Delete a product by its ID.

## HealthCheckContoller
- **Description**: Provides a simple health check endpoint to verify the application's status.
- **Endpoints**: `GET /health` 


## Models

### User

- **Package**: `com.example.ecommerce.models`
- **Table Name**: `users`
- **Description**: Represents a user in the e-commerce system.

| Field     | Type     | Description                          | Constraints          |
|-----------|----------|--------------------------------------|----------------------|
| email     | String   | Email address of the user            | Unique, Non-nullable |
| password  | String   | Password for user authentication     | Non-nullable         |
| firstName | String   | First name of the user               | Non-nullable         |
| lastName  | String   | Last name of the user                | Non-nullable         |
| roles     | Set<String> | Roles assigned to the user        | Fetched eagerly      |

#### Lifecycle Callbacks:
- `@PrePersist`: Initializes `createdAt` and `updatedAt` timestamps before the entity is persisted.

### Product

- **Package**: `com.example.ecommerce.models`
- **Table Name**: `products`
- **Description**: Represents a product available for purchase.

| Field          | Type       | Description                  | Constraints          |
|----------------|------------|------------------------------|----------------------|
| name           | String     | Name of the product          | Non-nullable         |
| description    | String     | Description of the product   | Up to 1000 characters|
| price          | BigDecimal | Price of the product         | Non-nullable         |
| stockQuantity  | Integer    | Quantity of the product in stock | Non-nullable         |

#### Lifecycle Callbacks:
- `@PrePersist`: Initializes `createdAt` and `updatedAt` timestamps.
- `@PreUpdate`: Updates the `updatedAt` timestamp.
