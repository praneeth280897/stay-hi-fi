# Use Java 17 (recommended for Spring Boot)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar app.jar

# Expose port (Render uses PORT env internally)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
