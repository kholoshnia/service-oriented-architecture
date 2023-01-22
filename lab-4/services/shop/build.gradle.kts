import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("java")
}

group = "ru.itmo.soa.lab"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11
extra["springCloudVersion"] = "Hoxton.SR8"

sourceSets {
    main {
        java {
            srcDir("src/main/kotlin")
            srcDir("build/generated-sources/jaxb")
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val jaxb by configurations.creating

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.10")

    jaxb("org.glassfish.jaxb:jaxb-xjc")
    implementation("wsdl4j:wsdl4j")
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.activation:activation:1.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-ribbon")
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    implementation("io.ktor:ktor-client:2.1.3")
    implementation("io.ktor:ktor-client-apache:2.1.3")
    implementation("io.ktor:ktor-client-logging:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:2.1.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs.plus("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("genJaxb") {
    ext["sourcesDir"] = "${buildDir}/generated-sources/jaxb"
    ext["schema"] = "src/main/resources/shop.xsd"

    ext["sourcesDir"]?.let { outputs.dir(it) }

    doFirst {
        ant.withGroovyBuilder {
            "taskdef"("name" to "xjc", "classname" to "com.sun.tools.xjc.XJCTask", "classpath" to jaxb.asPath)
            ext["sourcesDir"]?.let { mkdir(it) }

            "xjc"("destdir" to ext["sourcesDir"], "schema" to ext["schema"]) {
                "arg"("value" to "-wsdl")
                "produces"("dir" to ext["sourcesDir"], "includes" to "**/*.java")
            }
        }
    }
}

tasks.compileKotlin {
    dependsOn("genJaxb")
}
