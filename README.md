# 🚀 Spring Boot Microservices Project
### API Gateway • Eureka • JWT • Role-Based Authorization

## 📌 Project Overview
This is a production-ready **Spring Boot Microservices architecture** using **API Gateway**, **Eureka Service Registry**, and **JWT-based authentication & authorization**.

All security (JWT validation + role-based access) is handled centrally at the **API Gateway**, keeping backend microservices clean and scalable.

This project is suitable for:
- Freelance backend projects
- Enterprise microservices architecture
- Spring Boot real-world learning
- Secure REST APIs

---

## 🧱 Architecture (Logical View)

Client (Postman / Frontend)
        |
        v
API-GATEWAY (JWT + Role Validation)
        |
        v
---------------------------------
| USER-SERVICE                  |
| PRODUCT-SERVICE               |
| ORDER-SERVICE                 |
---------------------------------
        |
        v
EUREKA SERVER (Service Registry)

---

## 🛠️ Technologies Used
- Java 17
- Spring Boot
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Security
- JWT (jjwt)
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Docker (optional)

---

## 📂 Microservices

| Service Name | Port | Description |
|-------------|------|-------------|
| eureka-server | 8761 | Service Registry |
| api-gateway | 8080 | Gateway + Security |
| user-service | 8081 | Authentication & Users |
| product-service | 8082 | Product APIs |
| order-service | 8083 | Order APIs |

---

## 🔐 Security Design

### Authentication
- JWT generated in **USER-SERVICE**
- Token contains:
  - username
  - role (USER / ADMIN)
  - expiry time

### Authorization (Centralized at Gateway)

| API Path | Allowed Roles |
|--------|---------------|
| /auth/** | Public |
| /users/** | USER, ADMIN |
| /products/** | USER, ADMIN |
| /orders/** | USER |
| /admin/** | ADMIN |

---

## 🔑 JWT Flow
1. Client calls `/auth/login`
2. USER-SERVICE generates JWT
3. Client sends JWT in Authorization header
4. API-GATEWAY validates token and role
5. Request forwarded to backend service

---

## 📥 API Endpoints

### Login
POST /auth/login

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
