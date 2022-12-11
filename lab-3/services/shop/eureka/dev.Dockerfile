FROM adoptopenjdk:11-jre-hotspot
COPY build/libs/eureka-0.0.1.jar os-shop-eureka.jar
ENTRYPOINT ["java", "-jar", "/os-shop-eureka.jar"]
