FROM java:8
MAINTAINER CoderWdd
COPY /target/*.jar /docker-cooperation.jar
EXPOSE 3345:3345
ENTRYPOINT ["java","-jar","docker-cooperation.jar"]