# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/auth-service.jar /app/auth-service.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]
