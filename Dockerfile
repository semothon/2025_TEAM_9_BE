FROM openjdk:11
LABEL authors="passtry0331"
ARG JAR_FILE=build/libs/trithon-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]