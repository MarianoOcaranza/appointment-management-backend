# Appointments management

Appointments-Management is a backend project built with Spring Boot and tested with Postman. This was designed for practicing purposes and showcasing my backend development skills, including: Spring Boot layered architecture, Web security with JWT and cookies management, use of Docker in the development stages, among others.

## Technologies and Tools
- Java Spring Boot
- Spring Security
- JSON Web Tokens
- Cookie based authentication
- MySQL (With Docker)
- JPA/Hibernate
- DTOs for input validation
- Maven

### *** IMPORTANT! ***
As this project is in development stages, applications.properties file is not properly protected. 
The only environment variable you should set to test the application locally, is the JWT_SECRET (you can use any only key generator).
The rest of the properties, like the database root password or the admin password, is in plain text and you can change it as you want. In the future, this will be managed with environment variables for protection.


### *** This README is currently being developed ***
# In development important features:
    - Refresh token implementation
    - Complete CRUD for appointments (only 'create' has been done yet)
