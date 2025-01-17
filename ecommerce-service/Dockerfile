# Use openjdk:17-jdk-alpine as the base image for running the application
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built WAR file from the build context to the runtime stage
COPY target/ecommerce-0.0.1-SNAPSHOT.war ecommerce.war

# Expose the port that the application will run on
EXPOSE 8083

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "ecommerce.war"]
