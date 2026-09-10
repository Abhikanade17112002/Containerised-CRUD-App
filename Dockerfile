# ==========================================
# Stage 1: Build Spring Boot Application
# ==========================================

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Maven Wrapper
COPY mvnw .
COPY .mvn .mvn

# Maven project configuration
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build JAR
RUN ./mvnw clean package -DskipTests


# ==========================================
# Stage 2: Run Spring Boot Application
# ==========================================

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]