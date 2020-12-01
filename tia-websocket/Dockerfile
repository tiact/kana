FROM openjdk:8-jdk-alpine

MAINTAINER Rajeev Kumar Singh <f.n.k.kana@gmail.com>

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=target/tia-websocket-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} tia-websocket.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tia-websocket.jar"]

