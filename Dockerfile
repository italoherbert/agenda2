FROM openjdk:17
COPY ./target/agenda-0.0.1-SNAPSHOT.jar /tmp/agenda.jar
WORKDIR /tmp
CMD [ "java", "-jar", "/tmp/agenda.jar" ]