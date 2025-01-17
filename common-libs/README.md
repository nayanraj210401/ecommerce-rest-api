# Common Libraries

This directory contains shared libraries that are used across multiple microservices in the e-commerce application. These libraries include common DTOs (Data Transfer Objects), models, and utilities that are essential for the functioning of the application.

## Overview

The common-libs module provides a centralized location for shared code, ensuring consistency and reducing duplication across different services. By using these common libraries, we can maintain a single source of truth for shared components and facilitate easier updates and maintenance.

## Key Components

### 1. DTOs (Data Transfer Objects)
- **Purpose**: DTOs are used to transfer data between different layers of the application. They help in encapsulating the data and ensuring that only the required information is exposed.
- **Examples**:
  - `HealthDTO`: Represents the health status of a service.
  - `CreateOrderRequest`: Represents the request payload for creating an order.
  - `OrderDTO`: Represents the data transfer object for an order.
  - `ProductDTO`: Represents the data transfer object for a product.
  - `UserDTO`: Represents the data transfer object for a user.

### 2. Models
- **Purpose**: Models represent the entities in the application and are used for database interactions. They define the structure of the data and the relationships between different entities.
- **Examples**:
  - `BaseModel`: A base class for all entities, providing common fields like `id`, `createdAt`, and `updatedAt`.
  - `Order`: Represents an order placed by a user.
  - `OrderItem`: Represents an item in an order.
  - `Product`: Represents a product available for purchase.
  - `User`: Represents a user in the e-commerce system.

### 3. Enums
- **Purpose**: Enums are used to define a set of predefined constants. They help in representing fixed sets of values and improve code readability.
- **Examples**:
  - `OrderStatus`: Represents the status of an order (e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED).
  - `PaymentMethod`: Represents the different payment methods (e.g., CREDIT_CARD, PAYPAL).
  - `PaymentStatus`: Represents the status of a payment (e.g., SUCCESS, FAILED, PENDING).

### 4. Utilities
- **Purpose**: Utility classes provide common functionality that can be reused across different services. They help in reducing code duplication and improving maintainability.
- **Examples**:
  - `JwtUtils`: Provides utility methods for working with JSON Web Tokens (JWT).
  - `DateUtils`: Provides utility methods for working with dates and times.

## Usage

To use the common libraries in your microservices, add the `common-libs` dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>common-libs</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

By including this dependency, you can access the shared DTOs, models, enums, and utilities in your microservices, ensuring consistency and reducing duplication.

## Conclusion

The common-libs module plays a crucial role in the e-commerce application by providing shared libraries that are used across multiple microservices. By centralizing the common code, we can ensure consistency, reduce duplication, and facilitate easier updates and maintenance.
