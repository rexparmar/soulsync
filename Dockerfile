# Step 1: Build the app using Gradle
FROM gradle:8.5.0-jdk17 AS build
COPY . /home/app
WORKDIR /home/app
RUN gradle build -x test

# Step 2: Run the app using a lighter JDK
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
