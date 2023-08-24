# Task:

Implement a solution with a following scenario:

1. Customer wants to see their monthly balance and cumulative balance
2. There is an API that is developed by another team. The API provides bank transactions which include amount transferred and date
3. Things to consider
* In this task there is no need to develop the API developed by another team
* How to design your application so that it is testable?
* If the application must be deployed to a server in remote location, how would you do it?

# Solution:

1) Tech Stack used: Java17, Spring Boot, Maven, JUnit5, Mockito, Lombok, Docker
2) This is simple maven project design, not a full-fledged production ready spring boot application. Just to show the basic design of the application.
3) This project is designed to be testable, and it has unit tests and integration tests.
4) This project can be deployed to a server in remote location using **docker**. 
5) This project has a **docker-compose.yml** file, which can be used to build and run the docker image as a container.
