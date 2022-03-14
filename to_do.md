## To DO

If I have more time,I can add the below features for the next version to have a better product:


1) Develop a seperate application for authorazation. Fow now it is embeded in game application.If we want to use more than one instance or replica for this application, login services also will be more than once. It is not good idea. Login services should be one and main service. We can use here an api gateway for authorazation and routing the requests.
2) Implement a  fallback mechanism for MongoDB. Maybe I can implement circuit-breaker pattern for this. Ok I am using cache mechanism but if mongo db servers down, you should manage this situation. You should not try to connect for mongo db continuously when the servers down. If you implement fallback mechanism, this mechanism will handle this .
3) Add metric to capture more details and provide more data to create logging and monitoring system. (Grafana,Prometeus,Kibana ..)
4) Provide CI/CD scripts to make the CI/CD process automatically.
5) Refactor DBInit to use Spring batch for reading and storing csv file in database.
6) Use flyway or liquibase to provide automatic DB migration.
7) Use cache DB instead of in memory. (Redis,Cassandara..)
