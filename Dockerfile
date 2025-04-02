FROM openjdk:17
LABEL authors="passtry0331"
ARG JAR_FILE=build/libs/trithon-0.0.1-SNAPSHOT.jar

# Google Cloud 서비스 계정 키 파일 경로를 빌드 시 전달받기 위해 ARG 선언
ARG GOOGLE_APPLICATION_CREDENTIALS_PATH
# 전달받은 서비스 계정 키 파일을 Docker 이미지 내에 복사
COPY ${GOOGLE_APPLICATION_CREDENTIALS_PATH} /google_application_credentials.json

ADD ${JAR_FILE} docker-springboot.jar

# 애플리케이션 실행 시, GOOGLE_APPLICATION_CREDENTIALS 환경 변수 설정
ENV GOOGLE_APPLICATION_CREDENTIALS="/google_application_credentials.json"

ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]