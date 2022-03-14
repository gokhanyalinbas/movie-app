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

**Test Coverage**
```

[ all classes ]
Overall Coverage Summary
Package	Class, %	Method, %	Line, %
all classes	94,9% (37/ 39)	88,9% (136/ 153)	94% (327/ 348)

Coverage Breakdown
Package	Class, %	Method, %	Line, %
com.backbase.movie	50% (1/ 2)	25% (1/ 4)	20% (1/ 5)
com.backbase.movie.config	100% (2/ 2)	100% (10/ 10)	100% (46/ 46)
com.backbase.movie.config.jwt	100% (5/ 5)	100% (17/ 17)	97,1% (67/ 69)
com.backbase.movie.constant	0% (0/ 1)	0% (0/ 1)	0% (0/ 1)
com.backbase.movie.controller	100% (2/ 2)	100% (6/ 6)	100% (14/ 14)
com.backbase.movie.dto.request	100% (2/ 2)	100% (12/ 12)	100% (12/ 12)
com.backbase.movie.dto.response	100% (2/ 2)	81,8% (9/ 11)	81,8% (9/ 11)
com.backbase.movie.entity	100% (2/ 2)	91,7% (11/ 12)	93,3% (14/ 15)
com.backbase.movie.exception	100% (4/ 4)	85,7% (6/ 7)	90% (9/ 10)
com.backbase.movie.exception.handler	100% (1/ 1)	100% (8/ 8)	100% (12/ 12)
com.backbase.movie.model.api	100% (2/ 2)	81,8% (9/ 11)	81,8% (9/ 11)
com.backbase.movie.model.db	100% (6/ 6)	73,7% (14/ 19)	73,7% (14/ 19)
com.backbase.movie.model.file	100% (2/ 2)	88,9% (8/ 9)	88,9% (8/ 9)
com.backbase.movie.service.impl	100% (4/ 4)	100% (18/ 18)	100% (63/ 63)
com.backbase.movie.util	100% (2/ 2)	87,5% (7/ 8)	96,1% (49/ 51)
generated on 2022-03-15 01:02
```
