# Demo

API RESTful de creación de usuarios.

# Libraries

- [spring-boot](https://spring.io/projects/spring-boot)
- [spring-security](https://spring.io/projects/spring-security)
- [java-jwt](https://github.com/auth0/java-jwt)
- [h2database](https://www.h2database.com/html/main.html)

# Project

- Spring Security Configuration
- [JWT](https://jwt.io) Based Stateless Authentication
- Role Based Authorization
- Unit Testing

# Curls

#### (1) Register user request
````
curl --location --request POST 'http://localhost:8080/users/register-user' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdW' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=Juan Rodriguez Otro 26' \
--data-urlencode 'password=Aythi34' \
--data-urlencode 'email=juan.rodriguez@demo.cl'
````
#### Register user response
````
{
    "created": "07-jun-2021 at 04:30 AM",
    "modified": "07-jun-2021 at 04:30 AM",
    "last_login": "07-jun-2021 at 04:30 AM",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiBPdHJvIDI2IiwiaXNzIjoiYXV0aDAiLCJleHAiOjE2MjMwNTQ4NTYsImlhdCI6MTYyMzA1NDY1NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdfQ.XIX3otMb4gBu_jJyDcwgQlB430amJ8jdHLXKWEe7WyI",
    "active": "0",
    "identificador": "4b0cd328-e691-4b26-9f9f-4363241b02ee"
}
````
#### (2) Login usuario request
````
curl --location --request POST 'http://localhost:8080/users/login' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiBPdHJvIDI2IiwiaXNzIjoiYXV0aDAiLCJleHAiOjE2MjMwNTQ4NTYsImlhdCI6MTYyMzA1NDY1NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdfQ.XIX3otMb4gBu_jJyDcwgQlB430amJ8jdHLXKWEe7WyI' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=Juan Rodriguez Otro 26' \
--data-urlencode 'password=Aythi34'
````
#### Login usuario response
````
{
    "id": "44ae6189-2e28-4296-b66c-f40ab4d2a452",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiBPdHJvIDI2IiwiaXNzIjoiYXV0aDAiLCJleHAiOjE2MjMwNTQ4ODEsImlhdCI6MTYyMzA1NDY4MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdfQ.bcEuNvlbMKFnTuRxpBpD_w4RN3VP4PiP8idWN539Co4",
    "enabled": true
}
````
#### (3) Get users request
````
curl --location --request GET 'http://localhost:8080/users/get-users' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiBPdHJvIDI2IiwiaXNzIjoiYXV0aDAiLCJleHAiOjE2MjMwNTQ4NTYsImlhdCI6MTYyMzA1NDY1NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdfQ.XIX3otMb4gBu_jJyDcwgQlB430amJ8jdHLXKWEe7WyI'
````
#### Get users  response
````
[
    {
        "username": "Juan Rodriguez Otro",
        "email": "juan.rodriguez@demo.cl",
        "created": "07-jun-2021 at 03:03 AM",
        "modified": "07-jun-2021 at 03:03 AM",
        "last_login": "07-jun-2021 at 03:03 AM",
        "active": "0",
        "identificador": "1dd6f75b-0a0c-44fc-b729-2626f3694cb9"
    }
]
````
# Run
````
.\gradlew bootRun
````
# Test
````
.\gradlew test --i
````


