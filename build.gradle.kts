plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.10"
}

version = "0.1"
group = "com.example"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    kapt("io.micronaut.openapi:micronaut-openapi:4.0.2")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.views:micronaut-views-core")

    // Hibernate Reactive for MySQL - compatible with Micronaut 3.7.10
    implementation("io.micronaut.data:micronaut-data-hibernate-reactive:3.9.4")
    implementation("io.vertx:vertx-mysql-client:4.3.8")

    // JPA
    implementation("javax.persistence:javax.persistence-api:2.2")

    // Reactor for reactive programming
    implementation("io.projectreactor:reactor-core:3.4.30")
    implementation("io.micronaut.reactor:micronaut-reactor:2.5.6")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.7")

    // For database migrations
    implementation("io.micronaut.flyway:micronaut-flyway:5.5.0")
    runtimeOnly("org.flywaydb:flyway-mysql:8.5.13")
}

kapt {
    arguments {
        arg("micronaut.openapi.project.dir", projectDir.toString())
        arg("micronaut.openapi.views.spec", "swagger-ui.enabled=true,swagger-ui.theme=flattop")
    }
}

application {
    mainClass.set("com.example.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
}



