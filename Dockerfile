FROM eclipse-temurin:latest
COPY target/hecla-backend-1.0.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]