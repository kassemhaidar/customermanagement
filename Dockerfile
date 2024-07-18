FROM openjdk:17-jdk-alpine

# Set the active Spring profile to "docker"
ENV SPRING_PROFILES_ACTIVE=docker

MAINTAINER KH
COPY target/customermanagement-0.0.1-SNAPSHOT.jar  customermanagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/customermanagement-0.0.1-SNAPSHOT.jar"]