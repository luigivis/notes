FROM openjdk:21
MAINTAINER bymatt matthewreyesvanegasma@hotmail.com
COPY target/notes-0.1.jar notes.jar
CMD ["java", "-jar", "notes.jar"]
EXPOSE 5010