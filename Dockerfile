# Step 1: Use Gradle image to build the JAR
FROM gradle:8.5.0-jdk17 AS build
COPY --no-cache . /home/app
WORKDIR /home/app
RUN gradle build -x test

# Step 2: Use smaller JDK image to run the JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
