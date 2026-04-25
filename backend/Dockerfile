ARG MAVEN_IMAGE=maven:3.9.9-eclipse-temurin-17
ARG JRE_IMAGE=eclipse-temurin:17-jre

FROM ${MAVEN_IMAGE} AS build
WORKDIR /workspace

COPY maven-settings.xml ./maven-settings.xml
COPY pom.xml ./
RUN mvn -s maven-settings.xml -B -ntp -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -s maven-settings.xml -B -ntp -DskipTests package

FROM ${JRE_IMAGE}
WORKDIR /app

COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
