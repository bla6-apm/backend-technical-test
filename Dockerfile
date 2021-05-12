FROM openjdk:16-alpine
EXPOSE 8084
WORKDIR /app
COPY target/availability-api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "availability-api-0.0.1-SNAPSHOT.jar" ]
