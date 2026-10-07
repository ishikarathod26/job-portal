# MyJobPortal - Detailed Project Explanation & Interview Guide

This document explains the **MyJobPortal** application in simple, easy-to-understand language. It covers the complete architecture, data flow, technology stack choices, code walkthrough, and specific interview questions and answers to help you explain this project with total confidence in technical interviews.

---

## 💡 1. Project Overview

### What is MyJobPortal?
**MyJobPortal** is a web-based employment platform that connects **Employers** looking to post job opportunities with **Job Seekers** searching for their next career move.

### Core Functions:
1. **Employers** can register their company and publish job openings with details such as title, description, location, required experience, salary, and tech stack.
2. **Job Seekers** can browse job listings, search jobs in real-time by keywords/skills, view detailed job descriptions, and apply directly by submitting their profile and cover letter.
3. **API Clients (e.g., Mobile Apps, Postman)** can interact with full RESTful APIs to perform CRUD operations on jobs, employers, and applications.

---

## 🏗️ 2. Architecture & Data Flow

The project strictly follows the industry-standard **Layered Architecture pattern** (5 Layers):

```
Client (Browser / Postman)
      │
      ▼
┌─────────────────────────┐
│     Controller Layer    │  <-- Handles HTTP Requests & Responses
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│      Service Layer      │  <-- Contains Business Logic & Validation
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│    Repository Layer     │  <-- Executes SQL Database Queries (Spring Data JPA)
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│      Entity Layer       │  <-- Represents Database Tables as Java Objects
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│     MySQL Database      │  <-- Stores Data Permanently
└─────────────────────────┘
```

### End-to-End Execution Flow Example: Creating a Job Posting
1. **Client** fills out the Post Job form or sends a `POST /api/jobs` request with JSON data.
2. **Controller (`JobApiController` or `PageController`)** intercepts the request, maps the request payload into a `Job` object, and calls the `JobService`.
3. **Service (`JobService`)** applies business logic (sets post date to `LocalDate.now()`, sets status to `isActive = true`) and passes the entity to `JobRepository`.
4. **Repository (`JobRepository`)** extends `JpaRepository<Job, Long>`. Hibernate generates the SQL `INSERT INTO jobs (...) VALUES (...)` statement.
5. **MySQL Database** stores the record and assigns an auto-incremented primary key (`id`).
6. **Response** travels back: Database → Repository → Service → Controller → Client (JSON or redirect to jobs list).

---

## 🛠️ 3. Why Each Technology is Used

| Technology | Purpose in Project | Why We Chose It |
|---|---|---|
| **Java 17+** | Core programming language | Strongly typed, enterprise-standard, platform-independent |
| **Spring Boot 3** | Application framework | Rapid development, embedded Tomcat server, auto-configuration |
| **Spring Data JPA** | Data Access Abstraction | Eliminates boilerplate JDBC code; automatic query generation from method names |
| **Hibernate** | ORM (Object-Relational Mapping) | Maps Java objects (`@Entity`) directly to MySQL tables |
| **MySQL** | Relational Database Management System | Robust ACID-compliant relational storage for jobs, employers, and applications |
| **Thymeleaf** | Server-side Template Engine | Integrates seamlessly with Spring Boot MVC for server-rendered HTML pages |
| **Vanilla JS & CSS** | Frontend interactivity & styling | Clean, responsive UI without heavy frontend frameworks like React or Angular |

---

## 📂 4. Class-by-Class Walkthrough

### 1. Entities (`com.ishik.jobportal.entity`)
- **`Job.java`**: Annotated with `@Entity` and `@Table(name = "jobs")`. Stores job details. Includes `@ManyToOne` relationship to `Employer`.
- **`Employer.java`**: Annotated with `@Entity` and `@Table(name = "employers")`. Stores employer company details. Has `@OneToMany(mappedBy = "employer")` pointing to `Job`.
- **`JobApplication.java`**: Annotated with `@Entity` and `@Table(name = "job_applications")`. Stores job applications submitted by candidates.

### 2. Repositories (`com.ishik.jobportal.repository`)
- **`JobRepository.java`**: Extends `JpaRepository<Job, Long>`. Custom JPQL query `@Query` for multi-field search across title, description, and skills.
- **`EmployerRepository.java`**: Extends `JpaRepository<Employer, Long>`. Custom method `findByEmail(String email)`.
- **`JobApplicationRepository.java`**: Extends `JpaRepository<JobApplication, Long>`. Custom methods `findByJobId(Long jobId)` and `existsByEmailAndJobId(String email, Long jobId)`.

### 3. Services (`com.ishik.jobportal.service`)
- **`JobService.java`**: Implements business operations for jobs (create, update, delete, search, filter). Uses **Constructor Injection** for `JobRepository`.
- **`EmployerService.java`**: Handles employer operations, including validation for duplicate company email.
- **`JobApplicationService.java`**: Manages job applications, checks job existence before submitting, prevents duplicate applications, and updates status.

### 4. Controllers (`com.ishik.jobportal.controller`)
- **`PageController.java`**: Annotated with `@Controller`. Returns Thymeleaf template names (`"home"`, `"jobs"`, `"job-details"`, `"post-job"`, `"edit-job"`, `"apply"`).
- **`JobApiController.java`**: Annotated with `@RestController`. Returns JSON data for `/api/jobs` endpoints.
- **`EmployerApiController.java`**: Annotated with `@RestController`. Handles `/api/employers`.
- **`ApplicationApiController.java`**: Annotated with `@RestController`. Handles `/api/applications`.

---

## 🎯 5. Top Interview Questions & Answers

### Q1: What is the difference between `@Controller` and `@RestController` in Spring Boot?
**Answer:**
- `@Controller` is used to build traditional web applications that render HTML pages using template engines like Thymeleaf. Method return values represent view names.
- `@RestController` is a specialized version that combines `@Controller` and `@ResponseBody`. Method return values are serialized directly into HTTP response bodies as JSON or XML. In our project, `PageController` uses `@Controller` to serve HTML templates, while `JobApiController` uses `@RestController` to expose REST APIs.

### Q2: Why did you use Constructor Injection instead of `@Autowired` on fields?
**Answer:**
Constructor Injection is the recommended best practice in Spring because:
1. **Immutability:** Dependencies can be marked `final`.
2. **Testability:** Makes unit testing easier without needing a Spring container; we can pass mock dependencies via the constructor.
3. **Prevents Null Pointer Exceptions:** Ensures all required dependencies are initialized before the class is used.

### Q3: How does Spring Data JPA execute queries without explicit SQL code?
**Answer:**
Spring Data JPA uses interface proxying and method-name parsing. When we define method signatures in interfaces like `findByJobType(String jobType)` extending `JpaRepository`, Spring Data JPA automatically parses the method name at application startup and generates the corresponding SQL query (`SELECT * FROM jobs WHERE job_type = ?`). For complex queries, we use the `@Query` annotation with JPQL (Java Persistence Query Language).

### Q4: Explain the database relationship between `Job` and `Employer`.
**Answer:**
It is a **Many-to-One** relationship from `Job` to `Employer` (`@ManyToOne`), and a **One-to-Many** relationship from `Employer` to `Job` (`@OneToMany`). One employer can post multiple job listings, but each job listing belongs to one specific employer. In MySQL, `jobs` table has an `employer_id` column as a Foreign Key referencing `employers(id)`.

### Q5: How does Hibernate handle database table creation in your application?
**Answer:**
We configured `spring.jpa.hibernate.ddl-auto=update` in `application.properties`. When the application boots, Hibernate inspects all classes annotated with `@Entity` (`Job`, `Employer`, `JobApplication`), compares them with the MySQL schema, and automatically creates or updates the database tables to match the Java fields.

---

## 📋 Summary Checklist for Interview Presentation
- [x] Clear separation of concerns (Controller → Service → Repository → Entity → Database)
- [x] Constructor injection used across all service and controller components
- [x] Full RESTful API supporting standard HTTP verbs (`GET`, `POST`, `PUT`, `DELETE`)
- [x] Dynamic server-side rendering with Thymeleaf and real-time JavaScript REST client fetching
- [x] Reliable database persistence powered by Spring Data JPA and MySQL
