FROM openjdk:17
LABEL authors="passtry0331"
ARG JAR_FILE=build/libs/trithon-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar

# 애플리케이션 리소스 복사
COPY src/main/resources/google_application_credentials.json /google_application_credentials.json
# 환경 변수 설정 (컨테이너 내부 경로)
ENV GOOGLE_APPLICATION_CREDENTIALS="/google_application_credentials.json"

ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]