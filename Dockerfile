FROM openjdk:17
LABEL authors="passtry0331"
ARG JAR_FILE=build/libs/trithon-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar

ENV GOOGLE_APPLICATION_CREDENTIALS="src/main/resources/google_application_credentials.json"

ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]