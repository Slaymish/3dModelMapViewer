# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline

# Copy the project source code to the working directory
COPY src ./src

# Build the application and package it as a JAR file
RUN mvn package spring-boot:repackage

# Use a lightweight base image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port on which your application runs (change if necessary)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]