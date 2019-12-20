FROM gradle:6.0.1-jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build -Dorg.gradle.daemon=false --stacktrace

FROM openjdk:8-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]
