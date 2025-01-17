# Use openjdk:17-jdk-alpine as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
