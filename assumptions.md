- For inquiry of movies we suppose possibility of typing correct and complete name from the client is more than typing incorrect. I first check the title on DB. If not found , I make an API call to OMDB API. 

- For rating the movie we consider the clients need see the detail of the movie, because of this need, with the help of omdb API , first it will be served the detail of movie, and after that the users send the rate and retrieved movie title and boxOffice to the application. Also If needed,We can send movie_id from client to server. Because when users search movies, they can see details of movie from DB.

- Also we assume that there is a seperate user service for login and registering. User can register or login the application and client sends to server user information (user_id,user_name).

