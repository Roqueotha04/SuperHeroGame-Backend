# 🦸 SuperHero Game – Backend API

## Overview


**SuperHero Game** is a production-ready backend application built with **Java 17** and **Spring Boot 3**.  
It powers a superhero battle platform with secure authentication, personalized features, and real-world account lifecycle management.

The system handles:

- 📧 Email confirmation and password recovery flows
- 🐳 Docker containerization
- ☁️ Cloud-native deployment
- 🔑 Environment-based secure configuration
- 🔐 Secure JWT-based authentication
- 🛡 Password encryption with BCrypt
- 🏗 Clean layered architecture

This project was built to reflect how modern backend systems are structured, secured, and deployed in real production environments.

---

🌐 **Frontend (Angular – Vercel)**  
https://superherogame-xi.vercel.app/

⚙️ **Backend API (Render – Production)**  
https://superherogame-backend.onrender.com

🗄 **Database (PostgreSQL – Neon Cloud)**  
Hosted on Neon Tech

---

## 🛠 Tech Stack

- Java 17
- Maven
- Spring Boot 3
- PostgreSQL (Neon Cloud)
- Render (Backend Deployment)
- Vercel (Frontend Deployment – Angular)
- Docker
- Spring Security 6
- JWT (Auth0 Java JWT)
- Spring Data JPA
- JavaMailSender (SMTP – Gmail)
- Global Exception Handling (@ControllerAdvice)
---

## 🏗 Architecture

The project follows a clean layered architecture:

### 🏗 Layer Responsibilities

- 🎮 **Controllers** → REST endpoints exposed to the client
- 🧠 **Services** → Core business logic and rules enforcement
- 🔄 **Mapper** → DTO ↔ Entity transformation
- 📦 **DTO** → Request & Response models
- 🗃 **Entities** → JPA domain models mapped to the database
- 🗄 **Repositories** → Data access layer (Spring Data JPA)
- ❗ **Exception** → Custom exceptions + GlobalExceptionHandler
- ⚙️ **Config** → Security & Mail configuration
- 🔐 **Utils** → JWT generation and validation utilities

---

## 🔐 Security & Authentication

The system uses **Spring Security 6** with **stateless JWT authentication**.

### Authentication Flow

1. User signs up
2. Confirmation email is sent
3. User confirms account via token
4. User logs in and receives a JWT
5. JWT is required for protected endpoints

### Token Strategy

Three token types are implemented:

- Authentication Token
- Email Confirmation Token (1 hour expiration)
- Password Reset Token (30 minutes expiration)

Each token includes:

- HMAC256 signature
- Issuer validation
- Expiration validation
- Subject (User ID)

## 📧 Email Integration

Integrated using **JavaMailSender (Gmail SMTP)**.

Used for:

- Account confirmation
- Password recovery
- Confirmation email re-sending

This simulates a real production-grade authentication lifecycle.

---

## 🚀 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/signup` | Register new account |
| POST | `/api/auth/signin` | Authenticate and receive JWT |
| GET | `/api/auth/confirmEmail/{token}` | Confirm email |
| POST | `/api/auth/requestConfirmationEmail` | Resend confirmation |
| POST | `/api/auth/recoverPassword` | Send password reset email |
| PATCH | `/api/auth/resetPassword/{token}` | Reset password |

---

### 👤 User

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/user/getActualUser` | Get authenticated user |
| PATCH | `/api/user/updateEmail` | Update email |
| PATCH | `/api/user/updatePassword` | Update password |
| PATCH | `/api/user/agregarFavorito/{idHeroe}` | Add hero to favorites |
| PATCH | `/api/user/eliminarFavorito/{idHeroe}` | Remove hero from favorites |

---

### ⚔️ Battles

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pelea/getPeleas` | Get battles of logged user |
| POST | `/api/pelea/agregar` | Register new battle |

---

### 🩺 Health Check

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/status` | Service health check |

---

## ☁️ Cloud Deployment

### Backend
- Hosted on Render
- Public production environment

### Database
- PostgreSQL hosted on Neon Tech

### Frontend
- Angular application deployed on Vercel

This separation reflects a real-world production architecture:

Frontend → Backend API → Cloud Database

---

## 🔑 Environment Variables

The application uses environment variables for secure production configuration:

- EMAIL_PASS
- JWT_SECRET
- POSTGRESDATABASE
- POSTGRESHOST
- POSTGRESPASSWORD
- POSTGRESPORT
- POSTGRESUSERNAME

Sensitive credentials are never hardcoded.

---

## 📦 Run Locally

### How to Run
1_Clone the repository: 

    git clone <repo-url> 

2_Configure your database in application.properties. 

3_ Run the application with Maven: 

    mvn spring-boot:run 



