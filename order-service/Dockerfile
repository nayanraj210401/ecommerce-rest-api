# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/order-service.jar /app/order-service.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
