# Etapa 1: Construcción del proyecto usando Maven
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copia los archivos necesarios para compilar
COPY pom.xml .
COPY src ./src

# Construye el proyecto (sin ejecutar tests)
RUN mvn clean package -DskipTests

# Etapa 2: Contenedor final con OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copia el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que corre tu app Spring Boot (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
