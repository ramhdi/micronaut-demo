FROM gradle:7.6.1-jdk11 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon

COPY src ./src
RUN gradle build -x test --no-daemon

FROM gcr.io/distroless/java11-debian11:nonroot
WORKDIR /app
COPY --from=builder /app/build/libs/demo-*-all.jar /app/application.jar
EXPOSE 8085
USER nonroot
ENTRYPOINT ["java", "-jar", "/app/application.jar"]