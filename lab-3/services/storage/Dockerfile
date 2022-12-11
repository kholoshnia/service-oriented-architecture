# cache gradle
FROM gradle:jdk11-alpine as cache
ENV GRADLE_USER_HOME /home/gradle/cache-home
RUN mkdir -p $GRADLE_USER_HOME
WORKDIR /cache-app
COPY build.gradle.kts .
RUN gradle clean build --no-daemon

# build
FROM gradle:jdk11-alpine as builder
COPY --from=cache /home/gradle/cache-home /home/gradle/.gradle
WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY shared shared
COPY storage storage
RUN gradle -p storage bootJar --no-daemon

FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /app/storage/build/libs/storage-0.0.1.jar os-storage-service.jar
ENTRYPOINT ["java", "-jar", "/os-storage-service.jar"]
