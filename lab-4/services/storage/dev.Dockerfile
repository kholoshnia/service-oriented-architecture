FROM adoptopenjdk:11-jre-hotspot
COPY storage/build/libs/storage-0.0.1.jar os-storage-service.jar
ENTRYPOINT ["java", "-jar", "/os-storage-service.jar"]
