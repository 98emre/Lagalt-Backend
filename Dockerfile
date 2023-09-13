FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM openjdk:17-jdk-alpine AS final
WORKDIR /app
COPY --from=build /app/target/lagalt-backend.jar /app/lagalt-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]
