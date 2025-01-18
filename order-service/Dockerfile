# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Add a step to run `./mvnw clean package`
COPY . .
RUN ./mvnw clean package

# Copy the built JAR file to the container
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose the port that the application will run on
EXPOSE 8081

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "order-service.jar"]
