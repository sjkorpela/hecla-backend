FROM eclipse-temurin:latest
COPY target/hecla-backend-0.1.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]