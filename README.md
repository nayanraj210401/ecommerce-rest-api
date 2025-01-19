# E-commerce Microservices Project

This project is a microservices-based e-commerce application. It consists of several services, each responsible for a specific domain of the application. The services communicate with each other using REST APIs and are registered with a discovery service.

## Services

- **API Gateway**: Handles routing of requests to the appropriate microservices.
- **Common Libraries**: Contains shared code and utilities used by other services.
- **Discovery Service**: Manages service registration and discovery.
- **Order Service**: Manages order-related operations.
- **E-commerce Service**: Manages product, user, and payment-related operations.

## Instructions to Start the Project

1. **Clone the repository**:
   ```sh
   git clone https://github.com/nayanraj210401/ecommerce-rest-api.git
   cd ecommerce-rest-api
   ```

2. **Start the Discovery Service**:
   ```sh
   cd discovery-service
   ./mvnw spring-boot:run
   ```

3. **Start the API Gateway**:
   ```sh
   cd ../api-gateway
   ./mvnw spring-boot:run
   ```

4. **Start the Common Libraries**:
   ```sh
   cd ../common-libs
   ./mvnw clean install
   ```

5. **Start the Order Service**:
   ```sh
   cd ../order-service
   ./mvnw spring-boot:run
   ```

6. **Start the E-commerce Service**:
   ```sh
   cd ../ecommerce-service
   ./mvnw spring-boot:run
   ```

## High-Level Design (HLD)

```mermaid
%% Current Monolithic Architecture
graph TD
    subgraph "Current Monolithic Architecture"
    Mono --> DB1[(Single Database)]
    end

%% Proposed Microservices Architecture
    subgraph "Proposed Microservices Architecture"
    Client2[Client Applications] --> API[API Gateway]
    API --> LB2[Load Balancer]
    LB2 --> Mono[Monolithic App<br/>All Services Combined<br/>- User Service<br/>- Product Service<br/>- Payment Service<br/>]
    LB2 --> OS[Order Service Cluster]
    
    subgraph "Scaled Order Service"
    OS --> OS1[Order Service Instance 1]
    OS --> OS2[Order Service Instance 2]
    OS --> OS3[Order Service Instance n]
    end
    
    OS1 --> Cache[(Redis Cache)]
    OS2 --> Cache
    OS3 --> Cache
    
    OS1 --> MQ[Message Queue<br/>Kafka]
    OS2 --> MQ
    OS3 --> MQ
    
    OS1 --> OrderDB[(Order Database<br/>Sharded)]
    OS2 --> OrderDB
    OS3 --> OrderDB
    
    MQ --> NS[Notification Service]
    end
```



## Low-Level Design (LLD)

```mermaid
classDiagram
    class User {
        Long id
        String email
        String password
        String firstName
        String lastName
        Set<String> roles
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    class Order {
        Long id
        BigDecimal totalAmount
        OrderStatus status
        Long userId
        List<OrderItem> orderItems
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    class OrderItem {
        Long id
        Order order
        Product product
        Integer quantity
        BigDecimal unitPrice
        BigDecimal subtotal
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    class Product {
        Long id
        String name
        String description
        BigDecimal price
        Integer stockQuantity
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    class Payment {
        Long id
        String transactionId
        BigDecimal amount
        PaymentStatus status
        PaymentMethod paymentMethod
        Long orderId
        LocalDateTime paymentDate
        String failureReason
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    User "1" --> "0..*" Order : places
    Order "1" --> "0..*" OrderItem : contains
    OrderItem "1" --> "1" Product : refers to
    Order "1" --> "0..*" Payment : has
```
