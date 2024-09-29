FROM openjdk:21-slim AS builder
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-edteam-processor.jar
RUN java -Djarmode=layertools -jar api-edteam-processor.jar extract

FROM openjdk:21-slim
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", "--spring.config.location=classpath:/application.yml"]