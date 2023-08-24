FROM maven:3-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
RUN addgroup --gid 1000 -S app \
  && adduser -S -G app app
USER app
ENTRYPOINT ["java", "-jar", "/app/app.jar" ]