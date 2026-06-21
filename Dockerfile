# Stage 1 - Build
FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /build

# 1. Copy and install common libraries first
COPY prep-tracker-common-lib ./prep-tracker-common-lib
RUN mvn clean install -f prep-tracker-common-lib -DskipTests

# 2. Copy tracker-service POM and go offline
COPY prep-tracker-tracker-service/pom.xml ./prep-tracker-tracker-service/
WORKDIR /build/prep-tracker-tracker-service
RUN mvn dependency:go-offline

# 3. Copy tracker-service source and build
WORKDIR /build
COPY prep-tracker-tracker-service/src ./prep-tracker-tracker-service/src
WORKDIR /build/prep-tracker-tracker-service
RUN mvn clean package -DskipTests

# Stage 2 - Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/prep-tracker-tracker-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
