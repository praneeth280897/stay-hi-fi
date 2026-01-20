# ---------- Build stage ----------
FROM maven:3.8.8-openjdk-8 AS build

WORKDIR /build

# Copy pom.xml first (for dependency caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# ---------- Run stage ----------
FROM openjdk:8-jre-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
