FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY target/backend-projeto-temperatura-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/static /app/static
ENTRYPOINT ["java", "-jar", "/app.jar"]