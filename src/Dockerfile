FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
RUN mvn -f /home/app/pom.xml clean install -P generated-jooq -Ddb.url=jdbc:postgresql://192.168.12.6:5432/shareit -Ddb.user=postgres -Ddb.password=postgres
#
# Package stage
#
FROM openjdk:17.0.2-jdk-slim-buster
COPY --from=build /home/app/target/STM-labs-1.0-SNAPSHOT.jar /usr/local/lib/STM-labs.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/STM-labs.jar"]