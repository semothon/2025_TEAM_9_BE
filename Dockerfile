FROM openjdk:11

# ARG: 빌드 시점에 전달받을 변수
ARG JAR_FILE
ARG AUTHOR

# LABEL도 ARG로 처리
LABEL authors="${AUTHOR}"

# JAR 파일 복사
ADD ${JAR_FILE} docker-springboot.jar

# 실행 명령
ENTRYPOINT ["sh", "-c", "java -jar /docker-springboot.jar > app.log 2>&1"]