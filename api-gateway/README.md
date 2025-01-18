# API Gateway Service

The API Gateway service is a crucial component in a microservices architecture. It acts as a single entry point for all client requests, routing them to the appropriate backend services. This service provides various functionalities such as request routing, load balancing, security, and monitoring.

## Routes Configuration
The API Gateway service routes incoming requests to the appropriate backend services based on the following configurations:

### Order Service:

- Path: /orders/**
- URI: lb://ORDER-SERVICE

### E-commerce Service:

- Path: /api/**
- URI: lb://ECOMMERCE
These routes ensure that requests to /orders/** are directed to the Order Service and requests to /api/** are directed to the E-commerce Service.


## Configuration

The API Gateway service can be configured using the `application.properties` file. Here are some key configuration properties:

- `server.port`: Specifies the port on which the API Gateway service will run.
- `spring.application.name`: Sets the name of the API Gateway service.
- `eureka.client.serviceUrl.defaultZone`: Configures the Eureka server URL for service discovery.

## Dependencies

The API Gateway service relies on the following dependencies:

- `spring-cloud-starter-gateway`: Provides the core functionality for building an API Gateway.
- `spring-cloud-starter-netflix-eureka-client`: Enables the API Gateway to register with the Eureka server for service discovery.
- `jjwt`: Provides JSON Web Token (JWT) support for authentication and authorization.

## Running the Service

To run the API Gateway service, use the following command:

```bash
./mvnw spring-boot:run
```

Ensure that the Eureka server and other backend services are running before starting the API Gateway service.

## Health Check

The API Gateway service provides a health check endpoint to verify its status. You can access the health check endpoint at:

```
GET /actuator/health
```

This endpoint returns the health status of the API Gateway service and its dependencies.
