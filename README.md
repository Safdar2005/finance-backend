#  Finance Data Processing & Access Control Backend

##  Overview

This project is a backend system for a Finance Dashboard that manages users, financial records, dashboard analytics, and secure API access using JWT.

The system is built using Spring Boot, MySQL, and JWT Authentication with a clean layered architecture.

---

##  Key Features

###  User & Role Management
- Create users
- Assign roles (ADMIN, ANALYST, VIEWER)
- Manage active/inactive status

###  Authentication
- JWT-based login system
- Stateless authentication
- Secure API access

###  Financial Records
- Create records
- Read all records
- Update records
- Delete records

###  Filtering
- Filter records by category
- Filter records by type (INCOME / EXPENSE)

###  Dashboard APIs
- Total income
- Total expense
- Net balance
- Category-wise summary

---

##  Role-Based Access Control

| Role    | Permissions                |
|---------|---------------------------|
| ADMIN   | Full access (CRUD + Users) |
| ANALYST | Read + Summary            |
| VIEWER  | Read-only                 |

---

##  Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- MySQL
- JPA / Hibernate
- Swagger (OpenAPI)

---

## 🗄️ Database Setup

### Step 1: Create Database

CREATE DATABASE finance_db;

### Step 2: Configure application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/finance_db  
spring.datasource.username=root  
spring.datasource.password=YOUR_PASSWORD  

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

Replace YOUR_PASSWORD with your MySQL password.

---

##  API Endpoints

###  Authentication

POST /api/auth/login

Request:
{
  "email": "admin@gmail.com",
  "password": "1234"
}

Response:
{
  "token": "JWT_TOKEN"
}

---

###  Users

POST /api/users

Request:
{
  "name": "John",
  "email": "john@gmail.com",
  "password": "1234",
  "role": "ANALYST"
}

---

###  Financial Records

POST /api/records

Request:
{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "description": "Monthly salary"
}

Other APIs:

GET /api/records  
PUT /api/records/{id}  
DELETE /api/records/{id}  

---

###  Filtering

GET /api/records/filter?category=Salary  
GET /api/records/filter?type=INCOME  

---

###  Summary APIs

GET /api/records/summary/income  
GET /api/records/summary/expense  
GET /api/records/summary/net  
GET /api/records/summary/category  

---

##  Authorization

All protected APIs require:

Authorization: Bearer <JWT_TOKEN>

---

##  JWT Authentication Flow

1. User sends login request with email and password  
2. Server validates credentials  
3. JWT token is generated and returned  
4. Client stores token  
5. Client sends token in Authorization header  
6. Server validates token before processing request  

---

##  API Testing

Swagger UI:
http://localhost:8080/swagger-ui/index.html

Hoppscotch:
https://hoppscotch.io

---

##  Run Project

mvn clean install  
mvn spring-boot:run  

---

##  Project Structure

controller → API layer  
service → business logic  
repository → database access  
model → entities  
security → JWT and filters  
config → security configuration  

---

##  Technical Decisions

- Spring Boot used for structured backend development
- JWT used for stateless authentication
- MySQL used for relational database
- Spring Security used for role-based access control
- Layered architecture for clean code

---

##  Limitations

- Password encryption is basic (can improve using BCrypt)
- Pagination not implemented
- Trend analysis API not included

---

##  Future Improvements

- BCrypt password hashing
- Pagination and sorting
- Monthly trends
- Deployment on cloud

---

##  Author

Safdar Ali
