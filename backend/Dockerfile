FROM openjdk:17-alpine AS build
WORKDIR /workspace/app

COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew build -x test

FROM openjdk:17-alpine
ENV TZ=Asia/Seoul
COPY --from=build /workspace/app/build/libs/*.jar cloudbox.jar
ENTRYPOINT ["java","-jar","/cloudbox.jar"]
