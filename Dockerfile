# build stage
FROM maven:3.9.3-eclipse-temurin-17-alpine as build-stage

# Set the working directory inside the container
WORKDIR /app

COPY . .
RUN mvn clean package

FROM eclipse-temurin:17.0.8_7-jre-alpine as deploy
# Copy the executable JAR file and any other necessary files
COPY --from=build-stage /app/target/*.jar /app/app.jar

# Expose the port on which your Spring application will run (change as per your application)
EXPOSE 8080

# Set the command to run your Spring application when the container starts
CMD ["java", "-jar", "/app/app.jar"]
