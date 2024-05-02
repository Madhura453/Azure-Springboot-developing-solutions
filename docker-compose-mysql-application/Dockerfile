FROM openjdk:17
ADD target/docker-compose-mysql-application-0.0.1-SNAPSHOT.jar docker-compose-mysql-application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-compose-mysql-application.jar"]

