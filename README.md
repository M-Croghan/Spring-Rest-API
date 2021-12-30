# <img src="img/java.png" alt="java logo" width="70"/> RECIPE APP - RESTful JSON API with Spring Boot<img src="img/sboot.png" alt="spring boot logo" width="60"/>


# OVERVIEW


# TECHNOLOGIES USED
* ## <img src="img/sboot.png" alt="spring boot logo" width="40"/> Spring Boot
* ## <img src="img/java.png" alt="java logo" width="40"/> Java 
* ## <img src="img/pg.png" alt="postgres logo" width="40"/> PostgreSQL 
* ## <img src="img/jwt.png" alt="jwt logo" width="40"/> JSON Web Tokens
* ## <img src="img/post.png" alt="postman logo" width="40"/> Postman 
* ## <img src="img/intellij.png" alt="intellij logo" width="40"/> IntelliJ IDEA
* ## <img src="img/ubuntu.png" alt="ubuntu logo" width="40"/> Ubuntu  


# FUNCTIONALITY
| Endpoint |	Functionality | 	Access |
|----------|------------------|-------------|
| POST /auth/users/register   |	Registers a user |	PUBLIC |
| POST /auth/users/login |	Logs a user in |	PUBLIC |
|GET /api/categories |	Lists all categories |	PRIVATE |
|GET /api/categories/{id} |	Gets a single category with the suppled id |	PRIVATE|
| POST /api/categories |	Creates a new category |	PRIVATE |
|PUT /api/categories/{id} |	Updates a category with the suppled id |	PRIVATE|
|DELETE /api/categories/{id} |	Deletes a category with the suppled id |	PRIVATE|
|POST /api/categories/{id}/recipes |	Creates a new recipe in the given category |	PRIVATE|
|GET /api/categories/{id}/recipes |	List all recipes in the given category |	PRIVATE|
|PUT /api/categories/{id}/recipes/{id} |	Updates a recipe in the given category 	|PRIVATE|
|DELETE /api/categories/{id}/recipes/{id} |	Deletes a recipe in the given category |	PRIVATE|