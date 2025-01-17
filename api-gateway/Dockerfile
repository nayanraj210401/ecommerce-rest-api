# Use openjdk:17-jdk-alpine as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file to the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
