FROM gradle:8.5.0-jdk17 AS build
COPY . /home/app
WORKDIR /home/app
RUN gradle build -x test

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
