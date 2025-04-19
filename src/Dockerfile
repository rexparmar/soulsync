# Use OpenJDK base image
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the build output JAR
COPY build/libs/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
