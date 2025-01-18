# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Add a step to run `./mvnw clean package`
COPY . .
RUN ./mvnw clean package

# Copy the built JAR file to the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose the port that the application will run on
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
