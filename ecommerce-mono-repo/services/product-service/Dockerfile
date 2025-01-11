# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the project's build file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Package the application
RUN ./mvnw package

# Expose the port the application runs on
EXPOSE 8082

# Define the command to run the application
CMD ["java", "-jar", "target/product-service-0.0.1-SNAPSHOT.jar"]
