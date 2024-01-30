FROM openjdk:17.0.2-jdk-slim

COPY srvc-user-timeline-1.0.0-SNAPSHOT-runner.jar srvc-user-timeline.jar

CMD ["java", "-jar", "srvc-user-timeline.jar"]