# Discovery Service

The Discovery Service is a crucial component in a microservices architecture. It is responsible for managing the registration and discovery of microservices. This service uses Netflix Eureka to provide service discovery capabilities, allowing microservices to find and communicate with each other without hardcoding their locations.

## Key Features

- **Service Registration**: Microservices register themselves with the Discovery Service, providing their network locations.
- **Service Discovery**: Microservices can query the Discovery Service to find the network locations of other services.
- **Health Monitoring**: The Discovery Service monitors the health of registered services and removes any that are no longer healthy.

## Configuration

The Discovery Service can be configured using the `application.yml` file. Here are some key configuration properties:

- `server.port`: Specifies the port on which the Discovery Service will run.
- `spring.application.name`: Sets the name of the Discovery Service.
- `eureka.client.register-with-eureka`: Indicates whether the Discovery Service should register itself with Eureka.
- `eureka.client.fetch-registry`: Indicates whether the Discovery Service should fetch the registry information from Eureka.
- `eureka.client.service-url.defaultZone`: Configures the Eureka server URL for service discovery.

## Dependencies

The Discovery Service relies on the following dependencies:

- `spring-cloud-starter-netflix-eureka-server`: Provides the core functionality for building a Eureka server.
- `spring-boot-starter-test`: Provides testing support for Spring Boot applications.

## Running the Service

To run the Discovery Service, use the following command:

```bash
./mvnw spring-boot:run
```

Ensure that the other microservices are configured to register with the Discovery Service.

## Health Check

The Discovery Service provides a health check endpoint to verify its status. You can access the health check endpoint at:

```
GET /actuator/health
```

This endpoint returns the health status of the Discovery Service and its dependencies.
