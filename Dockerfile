FROM openjdk:8-jdk-alpine as builder
ADD . /src
WORKDIR /src
RUN ./mvnw clean package -DskipTests

FROM openjdk:8-jdk-alpine
LABEL maintainer="Arseniy Sobolev teomant@bk.ru"
COPY --from=builder /src/target/appointment-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=docker"]