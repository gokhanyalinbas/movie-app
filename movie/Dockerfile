FROM openjdk:11
ADD target/movieapp.war app.war
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://moviemongodb:27017","-jar","app.war"]