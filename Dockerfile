# Use the appropriate base image for building the application
FROM openjdk:21-slim AS builder

WORKDIR /app

# Copy the Maven descriptor file to the container
COPY pom.xml ./

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the source code to the container
COPY src ./src

# Set environment variables
ENV PROD_DB_HOST=monorail.proxy.rlwy.net
ENV PROD_DB_PORT=51376
ENV PROD_DB_NAME=railway
ENV PROD_DB_PASSWORD=kqTxFVPZXPAtVPLicVRPIipCIWzRJhfX
ENV PROD_DB_USERNAME=postgres
ENV PORT=8080

# Build the application
RUN mvn package

# Use a separate runtime image
FROM openjdk:21-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar ./app.jar

# Set environment variables
ENV PROD_DB_HOST=monorail.proxy.rlwy.net
ENV PROD_DB_PORT=51376
ENV PROD_DB_NAME=railway
ENV PROD_DB_PASSWORD=kqTxFVPZXPAtVPLicVRPIipCIWzRJhfX
ENV PROD_DB_USERNAME=postgres
ENV PORT=8080

# Expose the application port
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
