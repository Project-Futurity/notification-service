FROM openjdk:17-alpine

COPY target/application.jar application.jar

CMD java -jar /application.jar