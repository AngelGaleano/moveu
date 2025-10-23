# Etapa 1: Construcción del JAR
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

# Establecer codificación para evitar errores con caracteres especiales
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"

RUN mvn clean package -DskipTests


# Etapa 2: Imagen final
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
