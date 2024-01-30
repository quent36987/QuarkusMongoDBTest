# build the docker
echo "Starting to build the .jar file"
docker build -t quarkus-docker . -v /jar:/jar

docker exec -it quarkus-docker /bin/bash -c "cp /app/srvc-user-timeline/target/*.jar /jar/"
docker exec -it quarkus-docker /bin/bash -c "cp /app/srvc-search/target/*.jar /jar/"
docker exec -it quarkus-docker /bin/bash -c "cp /app/repo-social/target/*.jar /jar/"
docker exec -it quarkus-docker /bin/bash -c "cp /app/repo-post/target/*.jar /jar/"

docker stop quarkus-docker
echo "Finished building the .jar file"


