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
COPY shop shop
RUN gradle -p shop bootJar --no-daemon

FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /app/shop/build/libs/shop-0.0.1.jar os-shop-service.jar
ENTRYPOINT ["java", "-jar", "/os-shop-service.jar"]
