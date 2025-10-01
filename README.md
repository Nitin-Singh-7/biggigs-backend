# Biggigs Backend

Spring Boot + MySQL backend for **Biggigs Freelance Platform**.

## Features
- User authentication (Client / Freelancer)
- Freelancers can post gigs
- Clients can browse and order gigs
- Orders system (Pending, Accepted, Completed)
- Basic chat messaging

## Tech Stack
- Java 17, Spring Boot, Hibernate, MySQL
- REST APIs tested with Postman

## Run locally
1. Clone repo  
2. Create MySQL database `biggigs`  
3. Update `application.properties` with your MySQL user/password  
4. Run:
   ```bash
   mvn spring-boot:run
