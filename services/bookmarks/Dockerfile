FROM openjdk:8-jre-slim
ARG jar
EXPOSE 8080
COPY $jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]
