## Summary

TUI DX Backend technical Test

The base project uses lombok, so you have to install it. You can use the following guide https://www.baeldung.com/lombok-ide

## API: Spring boot + PostgreSQL + Docker

This is a personal API to service to get a way to register Booking and Flights, how many of they are, its name, age, job and so.

##Dependencies:
- Docker: https://docs.docker.com/get-docker/
- Docker Compose: https://docs.docker.com/compose/install/
- Maven: https://www.baeldung.com/install-maven-on-windows-linux-mac
- Java 8, please, use openJDK, you can install it from: https://adoptopenjdk.net

## Run
1. Run ```mvn package ``` in root base of project, it will create an executable.
2. To boot the project just open a Terminal Emulator and put the prompt in the source directory and run the command: ```docker-compose up``` just where the ```docker-compose.yml``` field is.
It will start two containers that ones is the database, and the other one is the application.
- Database: PostgreSQL => 13.1, named 'db' 
- Application: SpringBoot => 2.2.3.RELEASE, named 'availabilityApi'

When the docker starts the database will be populated with some dummy data. 
For more info just take a look:
- ```db/init.sh```
- ```docker-compose.yaml => services/db/volumes```

## How to use it
The endpoints of this API are:
- **GET /public/v1/**:  Will provide a resume of the endpoint.

To run the web interface known as Swagger justo go to: http://localhost:8080/swagger-ui/index.html