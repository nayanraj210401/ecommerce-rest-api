# Use maven:3.8.1-jdk-11 as the base image for building the application
FROM maven:3.8.1-jdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code to the working directory
COPY . .

# Run Maven to clean and install the application
RUN mvn clean install

# Use openjdk:11-jre-slim as the runtime image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
