# Use the OpenJDK 17 image to run your Spring Boot application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your Maven project and install dependencies
COPY . .

# Install Maven (if not already present in the image)
RUN apt-get update && apt-get install -y maven

# Build the Spring Boot application
RUN mvn clean install -DskipTests

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/StockPriceData-0.0.1-SNAPSHOT.jar"]
