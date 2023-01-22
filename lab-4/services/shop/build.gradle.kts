import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.0.1.RELEASE"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("java")
}

group = "ru.itmo.soa.lab"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11
extra["springCloudVersion"] = "Finchley.SR2"

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
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.10")
//    implementation("org.springframework.ws:spring-ws-core")

//    implementation("org.jdom:jdom2:2.0.6.1")
//    implementation("jaxen:jaxen:2.0.0")

    implementation("wsdl4j:wsdl4j")
    jaxb("org.glassfish.jaxb:jaxb-xjc:4.0.1")

//    jaxb("com.sun.xml.bind:jaxb-xjc:4.0.1")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    implementation("com.sun.xml.bind:jaxb-impl:4.0.0")
    implementation("javax.xml.soap:javax.xml.soap-api:1.4.0")
    implementation("com.sun.xml.messaging.saaj:saaj-impl:1.5.1")
    implementation("javax.activation:javax.activation-api:1.2.0")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-ribbon")
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    implementation("io.ktor:ktor-client:2.1.3")
    implementation("io.ktor:ktor-client-apache:2.1.3")
    implementation("io.ktor:ktor-client-logging:2.1.3")

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

//tasks.register("genJaxb") {
//    ext["sourcesDir"] = "${projectDir}/src/main/java"
//    ext["schema"] = "src/main/resources/shop.xsd"
//
//    doFirst {
//        ant.withGroovyBuilder {
//            "taskdef"("name" to "xjc", "classname" to "com.sun.tools.xjc.XJCTask", "classpath" to jaxb.asPath)
//            ext["sourcesDir"]?.let { mkdir(it) }
//
//            "xjc"("destdir" to ext["sourcesDir"], "schema" to ext["schema"]) {
//                "arg"("value" to "-wsdl")
//                "produces"("dir" to ext["sourcesDir"], "includes" to "**/*.java")
//            }
//        }
//    }
//}

//tasks.register("genJaxb") {
//    ext["sourcesDir"] = "${projectDir}/src/main/java"
//    ext["classesDir"] = "${buildDir}/classes/java/main"
//    ext["schema"] = "src/main/resources/shop.xsd"
//
//    ext["classesDir"]?.let { outputs.dir(it) }
//
//    doLast {
//        ant.withGroovyBuilder {
//            "taskdef"("name" to "xjc", "classname" to "com.sun.tools.xjc.XJCTask", "classpath" to jaxb.asPath)
//            ext["sourcesDir"]?.let { mkdir(it) }
//            ext["classesDir"]?.let { mkdir(it) }
//
//            "xjc"("destdir" to ext["sourcesDir"], "schema" to ext["schema"]) {
//                "arg"("value" to "-wsdl")
//                "produces"("dir" to ext["sourcesDir"], "includes" to "**/*.java")
//            }
//
//            "javac"("destdir" to ext["classesDir"], "source" to 1.8, "target" to 1.8, "debug" to true,
//                "debugLevel" to "lines,vars,source", "classpath" to jaxb.asPath) {
//                "src"("path" to ext["sourcesDir"])
//                "include"("name" to "**/*.java")
//                "include"("name" to "*.java")
//            }
//
//            "copy"("todir" to ext["classesDir"]) {
//                "fileset"("dir" to ext["sourcesDir"], "erroronmissingdir" to false) {
//                    "exclude"("name" to "**/*.java")
//                }
//            }
//        }
//    }
//}

tasks.register("genJaxb") {
    ext["sourcesDir"] = "${buildDir}/generated-sources/jaxb"
    ext["schema"] = "src/main/resources/shop.xsd"

    ext["sourcesDir"]?.let { outputs.dir(it) }

    doLast {
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
