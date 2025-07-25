# Appointments management

Appointments-Management is a backend project built with Spring Boot and tested with Postman. This was designed for practicing purposes and showcasing my backend development skills, including: Spring Boot layered architecture, Web security with JWT and cookies management, use of Docker in the development stages, among others.

### *** IMPORTANT! ***
As this project is in development stages, applications.properties file is not properly protected. 
JWT Secret is in plain text, as well as database root credentials. 
This will be changed in production.

## Technologies and Tools used
- Java Spring Boot
- Spring Security
- JSON Web Tokens
- Cookie based authentication
- MySQL (With Docker)
- JPA/Hibernate
- DTOs for input validation
- Maven

## Features
- Register user as Professional
- Register user as Patient
- Authentication & Authorization with cookie-based JWT and Spring Security
- Deactivate user
- Retrieve all professionals (paginated, 10 professionals per page)
- Retrieve professionals by search parameters (specialty or last name)
- Schedule appointments
- Cancel appointments
- Retrieve appointments

## Test in local:
A docker-compose.yml file is provided in this project. 
- Using the command `docker-compose up -d` in the root folder will set up the container with the MySQL database necessary to run the project. 
- Use `docker volumes ls` to make sure that 'consultorio_volume' has been created.
- Be aware that the container will be set up in port 3306. If you already have this port in use, you should change it inside the docker-compose.yml file.

After the docker container and docker volume are ready, you can test the endpoints with Postman by running `GestionTurnosApplication.java`

Testeable endpoints are visible in 
`http://localhost:8080/swagger-ui/index.html`
   

