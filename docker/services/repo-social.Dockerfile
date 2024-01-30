FROM openjdk:17.0.2-jdk-slim

COPY repo-social-1.0.0-SNAPSHOT-runner.jar repo-social.jar

CMD ["java", "-jar", "repo-social.jar"]