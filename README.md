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
username: lordran 
password: lordranpass  
