# Stage 1: build the jar with JDK + Gradle wrapper
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# Stage 2: run the jar on a small JRE image
FROM eclipse-temurin:21-jre
LABEL author="Zhebegenov Ersultan"
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
