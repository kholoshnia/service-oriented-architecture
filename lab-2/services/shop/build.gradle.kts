import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("war")
}

group = "ru.itmo.soa.lab"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    compileOnly("javax:javaee-api:8.0.1")
    compileOnly("javax.ws.rs:javax.ws.rs-api:2.1.1")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("org.jboss.resteasy:resteasy-jaxrs:3.15.3.Final")

    implementation("io.ktor:ktor-client:2.1.2")
    implementation("io.ktor:ktor-client-apache:2.1.2")
    implementation("io.ktor:ktor-client-logging:2.1.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
