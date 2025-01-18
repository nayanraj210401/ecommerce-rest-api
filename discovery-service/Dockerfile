# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Add a step to run ./mvnw clean package
RUN ./mvnw clean package

# Copy the built JAR file from the build stage
COPY target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar

# Expose the port that the application will run on
EXPOSE 8761

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
