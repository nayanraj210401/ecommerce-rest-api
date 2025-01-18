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

## HealthCheckContoller
- **Description**: Provides a simple health check endpoint to verify the application's status.
- **Endpoints**: `GET /health` 
