# define base docker image
FROM openjdk:17
LABEL maintainer="madhura.net"
ADD target/dockerforhelloword-0.0.1-SNAPSHOT.jar dockerforhelloword.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "dockerforhelloword.jar"]



# below is code for multi stage docker file



#FROM openjdk:8-jdk-alpine as builder
#RUN mkdir -p /app/source
#COPY . /app/source
#WORKDIR /app/source
#RUN ./mvnw clean package
#
#
#FROM builder
#COPY --from=builder /app/source/target/*.jar /app/app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]