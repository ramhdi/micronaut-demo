plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
    id("org.graalvm.buildtools.native") version "0.9.20"
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
    implementation("io.micronaut.data:micronaut-data-hibernate-reactive:3.9.4")
    implementation("io.vertx:vertx-mysql-client:4.3.8")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("io.micronaut.flyway:micronaut-flyway:5.5.0")
    runtimeOnly("org.flywaydb:flyway-mysql:8.5.13")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")

    // Test dependencies
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

kapt {
    arguments {
        arg("micronaut.openapi.project.dir", projectDir.toString())
        arg("micronaut.openapi.views.spec", "swagger-ui.enabled=true,swagger-ui.theme=flattop")
    }
}

application {
    mainClass.set("com.example.Application")
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

graalvmNative {
    binaries {
        named("main") {
            imageName.set("books-api")
            mainClass.set("com.example.Application")
            debug.set(false)
            verbose.set(true)
            fallback.set(false)

            buildArgs.add("--initialize-at-build-time=ch.qos.logback")
            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory")
            buildArgs.add("--initialize-at-build-time=org.slf4j.impl.StaticLoggerBinder")
            buildArgs.add("--enable-http")
            buildArgs.add("--enable-https")
            buildArgs.add("--allow-incomplete-classpath")
            buildArgs.add("-H:+ReportExceptionStackTraces")
            buildArgs.add("-H:+AddAllCharsets")
            buildArgs.add("-H:IncludeResources=.*\\.yml")
            buildArgs.add("-H:IncludeResources=.*\\.yaml")
            buildArgs.add("-H:IncludeResources=.*\\.properties")
            buildArgs.add("-H:IncludeResources=.*\\.xml")
            buildArgs.add("-H:IncludeResources=.*\\.sql")
            buildArgs.add("-H:IncludeResources=META-INF/swagger/.*")
            buildArgs.add("-H:IncludeResources=META-INF/swagger/views/.*")
        }
    }
    toolchainDetection.set(false)
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
}