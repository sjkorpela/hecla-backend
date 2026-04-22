# HE/CLA Backend #

*See repository [hecla-runtime-environment](https://www.github.com/sjkorpela/hecla-runtime-environment) for full project description!*

Backend layers of HE/CLA project:
* Maven Java Spring Boot backend as API request handler
* MongoDB as backend
* KeyCloak as authentication server


### How to run locally ###

Needs:
* JDK 21+ to build
* Docker to build and run

Instructions:
1. Run command `./mvnw install` to install dependencies.
2. Run command `./mvnw clean package -DskipTests` to build .jar file.
3. Run command `docker compose build` to build Docker image.
4. Run command `docker compose up` to start the Docker container.

Or just run command 
`./mvnw clean package -DskipTests && docker compose down && docker compose up --build`
to build and run the project.

### How to run tests ###

Needs:
* JDK 21+ to build
* Docker to build and run
* NPM and Node.js for Cypress
* Cypress config file at `cypress/support/config.js`.
  * See `cypress/support/config.example.js` for example of config file.

1. Run commands `./mvnw install` and `npm install`to install dependencies.
2. Run Maven tests with command `./mvnw test` to test service and repository layers.
3. Run command `./mvnw clean package -DskipTests && docker compose down && docker compose up --build` to build and run the server.
4. Run Cypress tests with command `npm run cy:run` to test API.
   * Cypress tests run on the live server, but shouldn't affect existing data.

### AI usage ###

Things AI has been used for during the project:
* AI was used to get Cypress set up for API testing