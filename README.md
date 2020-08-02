# Book Shop backend Web Application
This is a sample web application that demonstrates how to build a Kotlin + Spring Boot applications with a PostgreSQL database.

### Reference Documentation
A test application that demonstrates how to work with:
* **[Spring Boot](https://spring.io/projects/spring-boot)**
* **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**
* **[Spring Security](https://spring.io/projects/spring-security)**
* **[JWT (JSON Web Token)](https://jwt.io)**
* **[JUnit](https://junit.org/)**
* **[Mockito](https://site.mockito.org/)**
* **[PostgreSQL Database](https://www.postgresql.org/)**
* **[RESTful API](https://restfulapi.net)**

## Run the application

 - Install a PostgreSQL database.
 - To build a project use `./gradlew build`. 
 - Run it within your IDE or with `./gradlew bootRun`.

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
### Generating password hashes for new users
I'm using [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) to encode passwords.
