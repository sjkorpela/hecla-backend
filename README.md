# HE/CLA Backend #

Backend layers of HE/CLA project:
* Maven Java Spring Boot backend as API request handler
* MongoDB as backend


### How to run locally ###

Needs:
* JDK 21+ to build
* Docker to build and run

Instructions:
1. Run command `./mvnw clean package` to build .jar file.
2. Run command `docker compose build` to build Docker image.
3. Run command `docker compose up` to start the Docker container.

Or just run command 
`./mvnw clean package -DskipTests && docker compose down && docker compose up --build`
to build and run project.