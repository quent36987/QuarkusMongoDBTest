FROM openjdk:17.0.2-jdk-slim

COPY srvc-search-1.0.0-SNAPSHOT-runner.jar srvc-search.jar

CMD ["java", "-jar", "srvc-search.jar"]