# Utilisez une image Maven comme base
FROM maven:3.8.3-openjdk-17-slim AS builder

# Copiez les fichiers source de l'application dans le conteneur
WORKDIR /app
RUN mkdir /jar
COPY . .
RUN mvn -N io.takari:maven:wrapper
RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

#RUN cp /app/srvc-user-timeline/target/*.jar /jar/
#RUN cp /app/srvc-search/target/*.jar /jar/
#RUN cp /app/repo-social/target/*.jar /jar/
#RUN cp /app/repo-post/target/*.jar /jar/

# Exécutez la construction du package ./mvnw clean package -DskipTests
CMD ["tail", "-f", "/dev/null"]
