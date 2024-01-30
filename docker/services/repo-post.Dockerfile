FROM openjdk:17.0.2-jdk-slim

COPY repo-post-1.0.0-SNAPSHOT-runner.jar repo-post.jar

CMD ["java", "-jar", "repo-post.jar"]