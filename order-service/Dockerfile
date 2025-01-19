# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the project files to the Docker image
COPY . .

COPY /maven-dep /root/.m2

# delete maven-dep
RUN rm -rf /maven-dep

# Add a step to run `./mvnw clean package`
RUN ./mvnw clean package

# Copy the built JAR file to the container
RUN mv target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose the port that the application will run on
EXPOSE 8081

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "order-service.jar"]