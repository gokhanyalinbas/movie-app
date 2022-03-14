## Solution

This application indicates whether a movie won a “Best Picture” Oscar, given a movie’s title based on OMDB API and the
existing CSV file. I have parsed the CSV file and filtered by "Best Picture" category and stored in MongoDB. Ofcourse we can filter more data but we are dealing with movies. So I wanted to filter only "Best Pictures".I used a collection based on Movie object. The object contains Rate objects and Award object.
When the request come to server, first I check DB. If the movie not found in DB, I call the OMDB API to retrieve data. If API returns data, I also store them in MongoDB.

I provided an API for login purpose. Also, there are 3 APIs which are related to
movies. 
- First one is for rating Movies. You can rate the movie by title.And each rate object stored in related movie object.
- Second one indicates whether a movie won a “Best Picture”. 
- Third one if for getting top 10 rated movies ordered by boxOffice value.

I also used in memory cache. I am caching the movies object by title. If there is any update on movie object, I update the cache also.
Becase we don't need to read data from DB in some cases. In example, If the movie won  or not win  award, it  doesn't change anymore. So we can read it from cache.

### Prerequisites:

- Java 11
- Spring boot 2.5.5
- JWT
- JUnit 5
- Maven
- Lombok
- Docker
- Docker Compose
- MongoDB
