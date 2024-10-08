# Etapa 1: Construir la aplicación
FROM maven:3.8.4-openjdk-17 AS build

LABEL maintainer="Diego Flores <dfloresc1602@alumno.ipn.mx>"

WORKDIR /app

# Copiar los archivos de proyecto (pom.xml y código fuente)
COPY ./pom.xml ./
COPY ./src ./src

# Construir el proyecto sin ejecutar los tests
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM openjdk:17.0.2

WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/target/cvms-0.0.1-SNAPSHOT.jar ./

EXPOSE 8083

# Ejecutar el JAR
ENTRYPOINT ["java", "-jar", "cvms-0.0.1-SNAPSHOT.jar"]
