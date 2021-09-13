# UsersService

Simple REST API for users data from github api (api.github.com/users/).

### To run application:
```
$ mvn clean compile package  
$ java -jar target/users-service-0.0.1-SNAPSHOT.jar
```
Aplication runs on port 8088, api url: localhost:8088/api/users

### Endpoints:
- GET /api/users/{login} - get specific user data  
  
User data consists of:
- id
- login
- name
- type
- avatarUrl
- createdAt
- calculations (6.0 / followersNumber * (2 + publicRepos))
