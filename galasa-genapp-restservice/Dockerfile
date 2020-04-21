FROM openjdk:latest

RUN mkdir /genapp-restservice

COPY target/rest-service-0.0.1-SNAPSHOT.jar /genapp-restservice

RUN chmod -x /genapp-restservice/rest-service-0.0.1-SNAPSHOT.jar

EXPOSE 27/tcp

CMD java -jar /genapp-restservice/rest-service-0.0.1-SNAPSHOT.jar