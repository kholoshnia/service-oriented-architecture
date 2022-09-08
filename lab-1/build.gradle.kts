plugins {
    kotlin("jvm") version "1.6.21"
}

group = "ru.itmo.soa.lab"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
