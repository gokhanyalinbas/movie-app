- For unit test, just run **`clean`** - **`install`** command.
- I provided a postman collection which is existing in postman folder in the root of the project.
  Please import the postman collection in your postman app.
- You can also use swagger-ui. http://localhost:8085/swagger-ui.html
- First you have to login and get a api token. I didn't implement any user service or DB. We assume that there is a seprate service for storing and registering users. You can login with credentials below:

  username : **`user`**
  password : **`user`**
  
 - After that you can  test API with endpoints below:
 - http://localhost:8085/movies/rate
 - http://localhost:8085/movies/rated
 - http://localhost:8085/movies/award

