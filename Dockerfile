FROM eclipse-temurin:17-jdk
LABEL maintainer="isaiah IM"
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} museum.jar
COPY .env .env
ENTRYPOINT ["java","-jar","museum.jar"]
