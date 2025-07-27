FROM openjdk:17-oracle
COPY target/*.jar Tzms.jar
EXPOSE 8004
ENTRYPOINT ["java", "-jar", "Tzms.jar"]