FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM openjdk:17-jdk-alpine AS final
WORKDIR /app
COPY --from=build /app/target/lagalt-backend-0.0.1-SNAPSHOT.jar /app/demo.jar
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]
