# Appointments management

Appointments-Management is a backend project built with Spring Boot. It was designed with training purposes and to showcase my backend development skills, including: Spring Boot layered architecture, RESTFul endpoints design, Web security with JWT and cookies management, use of Docker in the development stages, among others.

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

## Entities & Relationships
![mediar_data_design](https://github.com/user-attachments/assets/3181ec9a-1f7b-40fb-a5c9-0338bfae666d)


## Features
### Authentication:
- Register user as Professional
- Register user as Patient
- Cookie-based JWT login and access permissions
- Deactivate user
<img width="845" height="640" alt="loginflow drawio" src="https://github.com/user-attachments/assets/35d8cbaa-8de4-4f23-8f2d-8fdc18768eaa" />

<img width="741" height="481" alt="authfilter drawio" src="https://github.com/user-attachments/assets/024a64a5-7ded-4f36-b68f-6f3e357061d9" />


### For professionals:
- Set availability days and hours
- Retrieve timeslots with 20 minutes of difference between them (example: 9:00, 9:20, 9:40...)

### For patients:
- Retrieve all professionals (paginated, 10 professionals per page)
- Retrieve professionals by search parameters (specialty, last name or localization)

### Appointments:
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
`https://localhost:8443/swagger-ui/index.html` 

