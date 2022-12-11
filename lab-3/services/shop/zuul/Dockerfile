# build
FROM gradle:jdk11-alpine as builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /app/build/libs/zuul-0.0.1.jar os-zuul-eureka.jar
ENTRYPOINT ["java", "-jar", "/os-zuul-eureka.jar"]
