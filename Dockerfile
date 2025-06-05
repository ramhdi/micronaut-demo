FROM ghcr.io/graalvm/graalvm-ce:22.3.1 AS static-builder
RUN gu install native-image
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle
COPY gradlew ./
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon
COPY src ./src
RUN ./gradlew nativeCompile --no-daemon \
    -Pmicronaut.native.args="--static --libc=glibc"

FROM gcr.io/distroless/java-base-debian12:nonroot
COPY --from=static-builder /app/build/native/nativeCompile/books-api /app/books-api
EXPOSE 8000
ENTRYPOINT ["/app/books-api"]