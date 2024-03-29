# Task:

Implement a solution with a following scenario:

1. Customer wants to see their monthly balance and cumulative balance
2. There is an API that is developed by another team. The API provides bank transactions which include amount transferred and date
3. Things to consider
* In this task there is no need to develop the API developed by another team
* How to design your application so that it is testable?
* If the application must be deployed to a server in remote location, how would you do it?

# Solution:

1) Tech Stack used: Java17, Spring Boot3, Maven, JUnit5, Mockito, Lombok, Docker
2) This is simple maven project design, not a full-fledged production ready spring boot application. Just to show the basic design of the application.
3) This project is designed to be testable, and it has unit tests and integration tests. 
4) This project can be deployed to a server in remote location using **jenkins pipeline + terraform + docker**. 
5) This project has a **docker-compose.yml** file, which can be used to build and run the docker image as a container.
6) If we need to add more components to the project like database or queue or cache or any other component, we can add it to the **docker-compose.yml** file.
7) If we decide to use cloud services like AWS, we can write the deployment script using **terraform and ansible**.
8) OpenAPI documentation is available at: http://localhost:8080/swagger-ui/index.html

# Unknowns:

1) External API is not available, it can have pagination. >>  can be handled by **passing page number and size**.
2) It can have authentication and authorization. >>  can be handled by **passing token**.
3) It can have rate limiting. >> can be handled **with spring-retry**.
4) External API might not be available all the time. >> can be handled **with timeout**.
5) Data retrieval from external API might be time-consuming. >> can be handled **with @Async or CompletableFuture**.
6) External API might be down. >> can be handled **with circuit breaker**.
7) What if the external API change the response. >> can be handled with **extensible design**.
8) What if the external API brings breaking change. >> need to handle with **versioning and previous version support**.
9) Our application can have different profiles like dev, qa, prod. >> can be handled with **spring profiles**.


# CodeCoverage Report:

1) CodeCoverage report can be found under the '/demo/coverage/index.html' folder
2) Current coverage percentage: >75%

# Feedback:

 Our grading is quite heavy for this specific senior position. Some notes from our side:

    TDD was not followed per commit history
    Many things implemented at once in one commit, commits are not granular nor atomic and introduce many concepts at one
    Code is quite hard to follow here and there, and formatting is not standardized(extra line breaks here and there, weird indentations in some files)
    Really good that there is a lot of test coverage. I am worried about the amount of mocking services and other layers from the code. This would be hard to refactor now, as any refactoring would require many test changes as well
