# Use openjdk:17-jdk-slim as the base image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Add a step to run ./mvnw clean package
COPY . .
RUN ./mvnw clean package

# Copy the built WAR file from the build context to the runtime stage
RUN mv target/ecommerce-0.0.1-SNAPSHOT.war ecommerce.war

# Expose the port that the application will run on
EXPOSE 8083

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "ecommerce.war"]
