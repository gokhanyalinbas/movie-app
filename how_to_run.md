
Prerequisite:
Have Docker installed on your computer and be sure about Docker engine is stared.

 Firstly let's create a MongoDB from Docker hub.

-**Mongo DB**
- [x] I have used mongo image from docker hub. If you want to use local mongo db, just change datasourceUrl in applicaiton.properties at springboot application or use VM argument : **`-Dspring.data.mongodb.uri=mongodb://localhost:27017`**
- [x] pull mongo image from docker hub **`docker pull mongo:latest`**
- [x] run mongo image **`docker run -d -p 27017:27017 --name moviemongodb mongo:latest`**
- [x] login to mongo terminal to verify records **`docker exec -it moviemongodb bash`**
- type mongo and enter
- show dbs
- use Backbase
- show collections
- db.movies.find().pretty()


Second, Let's build and run API.

-**Backend**

* **without docker**
- [x] If you run springboot application on local, please change mongodb.url in application.properties or use VM argument.
- [x] Open movie-app folder in your spring IDE (IntellIJ,Eclipse, STS ..) or command prompt and run **`clean`** - **`install`** command.
- [x] After build you can run .war file.
- [x] Application link : 'http://localhost:8085'
  
* **with docker**
- [x] First we have to run **`clean`** - **`install`** command and be sure about that you have .war file under /target folder.
- [x] dockerize spring boot application **`docker build -t movie-app .`**
- [x] check docker running containers  **`docker ps`** 
- [x] If you have container for this image kill running container:
```
docker rm <containerId>
```

#### docker-compose.yml
```yaml
version: "3"
services:
  moviemongodb:
    image: mongo:latest
    container_name: "moviemongodb"
    hostname: moviemongodb
    ports:
      - 27017:27017
  movie-app:
    image: movie-app
    container_name: movie-app
    ports:
      - 8085:8085
    links:
      - moviemongodb
```

- [x] navigate to resources folder:
/src/main/resources and run  **`docker compose up -d`**
- [x] Be sure about all containers are running succesfully
 ```
[+] Running 2/2
 - Container moviemongodb  Running                                                                                                                            0.0s
 - Container movie-app     Running   
```



**Links**
- [x] API        :  http://localhost:8085
- [x] Mongo DB   :  mongodb://localhost:27017
- [x] Swagger UI :  http://localhost:8085/swagger-ui.html



If you have any problem with docker, let it go :) Just deploy your local PC and run the applciation.


