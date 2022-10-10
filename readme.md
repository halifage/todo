# TODO Application
This is a backend server to handle various CRUD operations for a client-side TODO application.

The application exposes 4 endpoints to GET, POST, UPDATE and DELETE. 

Authentication is currently implemented via the `userId` property of the Todo object.

A custom exception handler is implemented to return consistent information. 

## Requirements
1. Ubuntu 18.04 and above
2. Java 17
3. Maven 3.8 and above
4. Redis (running locally or elsewhere)
5. Git
6. cURL

# Setup
### 1. Redis
- On an Ubuntu server, install Redis:`sudo apt install redis`
- Check installation: `redis-cli --version`

**NB:** *By default the Redis server is started on Port 6379*

### 2. Git
- Install Git: `sudo apt instal git`

### 3. cURL
- Install cURL: `sudo apt isntal curl`

### 4. Spring Boot Server 
- Navigate to the preferred directory: `cd [path to directory]`
- Clone project from Github: `git clone https://github.com/halifage/todo.git`
- Navigate to the project's root folder: `cd todo/`

- Configure Redis server host and port:

   If the Redis server is running on a host and/or port other than the default, update `application.properties` file:

   `sudo nano src/main/resources/application.properties`

- Build server: `./mvnw clean install`
- Run the server: `java -jar target/todo-0.0.1-SNAPSHOT.jar`


# cURL Commands
### 1. GET
Fetch all TODO items

**NB:** *The `userId` property is required with a value has to be greater than 0.*

`curl localhost:8080/todo?userId=1`

### 2. POST
Add a TODO item to the data source.

There is validation on some fields.

`curl -X POST -H "Content-Type: application/json" 
-d '{"id":1,"userId":1,"description":"first todo","dueDate":"2022-10-11","state":"TODO"}'
localhost:8080/todo`

### 3. PUT
Update a TODO item in the data source (or add it if it doesn't exist).

There is validation on some fields.

`curl -X PUT -H "Content-Type: application/json"
-d '{"id":1,"userId":1,"description":"updated first todo","dueDate":"2022-10-12","state":"IN_PROGRESS"}' \
localhost:8080/todo`

### 4. DELETE
Delete a TODO item from the data source.

There is validation on some fields.

`curl -X DELETE -H "Content-Type: application/json" 
-d '{"id":1,"userId":1,"description":"updated first todo","dueDate":"2012-10-12","state":"IN_PROGRESS"}' 
localhost:8080/todo`
