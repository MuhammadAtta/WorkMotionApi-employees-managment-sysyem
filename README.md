# WorkMotionApi-employees-managment-sysyem
- Coding task /senior software engineer / Java position.

http://localhost:9091/api/swagger-ui/index.html?configUrl=/employee/v3/api-docs/swagger-config#/


As a part of our backend engineering team, you will be responsible for building our core platform including an  employees managment system.

The employees on this system are assigned to different states, Initially when an employee is added it will be assigned "ADDED" state automatically .


The other states (State machine) for A given Employee are:
- ADDED
- IN-CHECK
- APPROVED
- ACTIVE


Our backend stack is:
- Java 11 
- Spring Framework 
- Kafka

Your task is to build  Restful API doing the following:
- An Endpoint to support adding an employee with very basic employee details including (name, contract information, age, you can decide.) With initial state "ADDED" which incidates that the employee isn't active yet.

- Another endpoint to change the state of a given employee to "In-CHECK" or any of the states defined above in the state machine 


Please provide a solution with the  above features with the following consideration.

- Being simply executable with the least effort Ideally using Docker and docker-compose or any smailiar approach.
- For state machine could be as simple as of using ENUM or by using https://projects.spring.io/spring-statemachine/ 
- Please provide testing for your solution.
- Providing an API Contract e.g. OPENAPI spec. is a big plus

