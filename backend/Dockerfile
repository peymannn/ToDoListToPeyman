FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build

# Copy pom and source, then build the jar inside the Maven image
COPY pom.xml ./
COPY src ./src
RUN mvn -DskipTests package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /build/target/todo-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
