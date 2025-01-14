# E-commerce Services

This repository contains the service layer for an e-commerce application. The services handle business logic and interact with the data access layer to perform operations related to orders, authentication, payments, and products.

## Services Overview

### 1. OrderService
- **Purpose**: Manages order-related operations.
- **Key Methods**:
  - `getAllOrders()`: Retrieves all orders.
  - `getOrderById(Long id)`: Fetches a specific order by its ID.
  - `createOrder(CreateOrderRequest request)`: Creates a new order.
  - `updateOrderStatus(Long id, OrderStatus newStatus)`: Updates the status of an existing order.

### 2. AuthService
- **Purpose**: Handles user authentication and registration.
- **Key Methods**:
  - `register(RegisterRequest request)`: Registers a new user.
  - `login(LoginRequest request)`: Authenticates a user and generates a JWT token.

### 3. PaymentService
- **Purpose**: Manages payment processing and related operations.
- **Key Methods**:
  - `processPayment(PaymentRequest paymentRequest)`: Processes a payment.
  - `getPaymentByTransactionId(String transactionId)`: Retrieves payment details by transaction ID.
  - `refundPayment(String transactionId)`: Processes a refund for a payment.

### 4. ProductService
- **Purpose**: Manages product-related operations.
- **Key Methods**:
  - `getAllProducts()`: Retrieves all products.
  - `getProductById(Long id)`: Fetches a specific product by its ID.
  - `createProduct(CreateProductRequest productDTO)`: Creates a new product.
  - `updateProduct(Long id, ProductDTO productDTO)`: Updates an existing product.
  - `deleteProduct(Long id)`: Deletes a product by its ID.

### 5. CustomUserDetailsService
- **Purpose**: Provides user details for authentication purposes.
- **Key Methods**:
  - `loadUserByUsername(String email)`: Loads user details by email for authentication.

### 6. JwtService
- **Purpose**: Manages JWT token generation and validation.
- **Key Methods**:
  - `generateToken(UserDetails userDetails)`: Generates a JWT token for a user.
  - `isTokenValid(String token, UserDetails userDetails)`: Validates a JWT token.