FROM adoptopenjdk:11-jre-hotspot
COPY build/libs/config-0.0.1.jar os-shop-config.jar
ENTRYPOINT ["java", "-jar", "/os-shop-config.jar"]
