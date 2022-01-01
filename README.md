# <img src="img/java.png" alt="java logo" width="70"/>RESTful JSON API with Spring Boot<img src="img/sboot.png" alt="spring boot logo" width="60"/>

# OVERVIEW
In preparation for a large scale project involving API development, I re-wrote this application as a way to move through
each level and solidify my understanding of how each step works together. I had been exposed to a wide range of 
new concepts and technologies. This API was developed using Spring Boot and makes use of Hibernate / JPA to store, 
access, and manage Java objects in a PostgreSQL database. I expanded my understanding of authorization & authentication 
through the use of JSON Web Tokens (JWT) and the many ways in which Spring helps applications hit the ground running.

# TECHNOLOGIES USED 
<img src="img/sboot.png" alt="spring boot logo" width="40"/><img src="img/java.png" alt="java logo" width="50"/><img src="img/pg.png" alt="postgres logo" width="40"/><img src="img/jwt.png" alt="jwt logo" width="65"/><img src="img/post.png" alt="postman logo" width="40"/><img src="img/ubuntu.png" alt="ubuntu logo" width="65"/><img src="img/intellij.png" alt="intellij logo" width="40"/>  

* Spring Boot 2.6.2
* Java 11
* PostgreSQL
* JWT
* Postman
* Ubuntu / WSL
* IntelliJ IDEA (Professional 2021.2.2)


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
|POST /auth/api/profile |	Create a user profile current user | 	PRIVATE |
UPDATE /auth/api/profile |	Update the user profile for current user |	PRIVATE |