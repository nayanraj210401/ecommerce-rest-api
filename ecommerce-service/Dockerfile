# Use maven:3.8.1-jdk-11 as the base image for building the application
FROM maven:3.8.1-jdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code to the working directory
COPY . .

# Run the Maven build to create the application JAR file
RUN mvn clean install

# Use openjdk:11-jre-slim as the base image for running the application
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage to the runtime stage
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce.jar

# Expose the port that the application will run on
EXPOSE 8083

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]
