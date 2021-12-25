FROM openjdk:latest
COPY target/kyruus1-1.0-SNAPSHOT.jar /usr/bin/kyruus1-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar" , "/usr/bin/kyruus1-1.0-SNAPSHOT.jar"]