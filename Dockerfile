# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app
ENV PORT 8080
# Expose port 8080 (or any port your app runs on)
EXPOSE 8080
# Copy the built JAR file into the container
COPY target/*.jar /app/app.jar
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
