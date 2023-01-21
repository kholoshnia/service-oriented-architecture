FROM adoptopenjdk:11-jre-hotspot
COPY build/libs/zuul-0.0.1.jar os-shop-zuul.jar
ENTRYPOINT ["java", "-jar", "/os-shop-zuul.jar"]
