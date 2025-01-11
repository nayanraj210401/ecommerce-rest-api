# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the project's build file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "target/order-service-1.0.0.jar"]
