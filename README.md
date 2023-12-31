# Lordran initializer

A core of a Spring Boot project with all what is needed for a project , which contains :
- User registration ( Modifyable to your needs )
- Role based permisions
- Authentication management
- CSRF protection
- JWT

## Stack used
- Spring Security
- Spring Data JPA
- GraphQL
- Docker
- PostgreSQL
- JsonWebTokens
- Hibernate

## Core Files
In this section im going to explain only the most important parts to understand what the code does

### Compose.yml (optional)
This docker compose file give us a PostgreSql 15.1 and a web pgAdmin 4.
> docker compose up

After excecuting the command above , docker will create a volume where all the data of the DB will be stored.
The DB will be served at port 5432 and pgAdmin at port 80

I recommend to change the environments to ones of your choice

### Properties.yml
This is the part where we make the connection to our docker Postgres database.
The docker compose file will create a server with a database named **initializer_db** , you can change the name of the DB in the docker compose in case you are using docker.
In case you are not using docker , you can create the DB mannualy with your own credentials.

> spring:
>>datasource:
>>>url: jdbc:postgresql://localhost:5432/initializer_db
>>>username: lordran 
>>>password: lordranpass  

### ProjectSecurityConfig
This class is the responsable of all the web security problems that a project can deal with
- cors
- csrf
- context
- security filters
- endpoint protection

### Authentication Provider
This class is the responsable of taking the authentication request from the frontend and add it to the context of the application

### CsrfTokenFilter
This class extends the OncePerRequestFilter and makes sure to send a token with the shape of a cookie with the name of XSRF-TOKEN. The backend expects to get back the same cookie with the name of X-XSRF-TOKEN

### JWTGeneratorFilter
This class creates an access token with a sign that I wrote as a constant ( which I recommend you to change ) , it sets a creation and expiration time of 4 hours ( I also recommend to change the expiration time acording to your project's needs ).

The token is sent as a header and it needs to be append as a header for each response for authentication and authorization purposes.

### JWTValidatorFilter
This class validates the token that is sent in the header response by checking if it is not expired and if it has valid data inside.

If the token is invalid , it responses with the http status of the problem

## Endpoints

http://localhost:8080/roles/seed

http://localhost:8080/auth/register
{
"username":  "Blanca Sofia",
"email":  "blanca@sofia.com",
"password":  "dantepass"
}

http://localhost:8080/auth/login

### Headers

##### X-XSRF-TOKEN
Cookie for CSRF protection, it needs to be sent and recieved on each request
#### Authorization
Custom header with JWT for managing the session