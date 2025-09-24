# Multi-stage Dockerfile for Spring Boot application with JDK 17

# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build

# Install necessary tools for building
RUN apk add --no-cache bash

# Set working directory
WORKDIR /app

# Copy gradle wrapper and build files
COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code
COPY src src

# Build the application (this creates the JAR file)
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-alpine AS runtime

# Create non-root user for security
RUN addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

# Install curl for health checks
RUN apk add --no-cache curl

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appuser /app
USER appuser

# Expose the application port
EXPOSE 8080

# Set JVM options for containerized environments
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Dspring.profiles.active=docker"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]