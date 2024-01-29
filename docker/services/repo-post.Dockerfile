FROM openjdk:17.0.2-jdk-slim

COPY repo-post-1.0.0-SNAPSHOT.jar repo-post-1.0.0-SNAPSHOT.jar

CMD ["java", "-jar", "repo-post-1.0.0-SNAPSHOT.jar"]