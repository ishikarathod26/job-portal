# MyJobPortal - Spring Boot Job Portal Application

MyJobPortal is a full-stack web application built using **Java**, **Spring Boot**, **Spring Data JPA**, **MySQL**, and **Thymeleaf**. It provides a clean, production-ready implementation of a job portal where employers can post and manage job opportunities, and job seekers can search, filter, view details, and apply for positions.

---

## 🌟 Key Features

- **Job Management (CRUD):**
  - Create/Post new job openings
  - View all job listings with dynamic UI cards
  - View detailed job information
  - Update job details
  - Delete job postings
- **Advanced Job Search & Filtering:**
  - Real-time keyword search across title, description, and skills via REST API
  - Filter jobs by job type (Full-time, Part-time, Internship, Contract)
  - Filter jobs by location
- **Job Application System:**
  - Submit applications directly for any job listing
  - Duplicate application check (prevents multiple applications per email per job)
  - Update application statuses (PENDING, REVIEWED, ACCEPTED, REJECTED)
- **Employer Management:**
  - Employer profiles linked to job postings via `@ManyToOne` database relationships
- **Automated Data Seeding:**
  - Pre-populates sample employers and job listings on first startup if database is empty

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| **Language** | Java 17 / 25 |
| **Framework** | Spring Boot 3.2.5 |
| **Web & REST** | Spring Web (`@RestController`, `@Controller`) |
| **Persistence** | Spring Data JPA / Hibernate |
| **Database** | MySQL Database |
| **Templating** | Thymeleaf + HTML5 + CSS3 + Vanilla JavaScript |
| **Build Tool** | Apache Maven |

---

## 📁 Project Architecture

The project follows the standard **Client/Frontend → Controller → Service → Repository → Database** architecture:

```
[ Frontend / Client ]
  │
  ├── Web UI (Thymeleaf / HTML / CSS / JavaScript)
  └── REST Clients (Postman / HTTP Requests)
        │
        ▼
[ Controller Layer ] (`com.ishik.jobportal.controller`)
  │ Handles incoming HTTP requests and returns JSON responses or Thymeleaf view names.
  ├── PageController (Serves Thymeleaf HTML views)
  ├── JobApiController (REST API for Jobs: CRUD, search, filter)
  ├── EmployerApiController (REST API for Employers)
  └── ApplicationApiController (REST API for Job Applications)
        │
        ▼
[ Service Layer ] (`com.ishik.jobportal.service`)
  │ Encapsulates business rules and logic. Uses Constructor Injection.
  ├── JobService
  ├── EmployerService
  └── JobApplicationService
        │
        ▼
[ Repository Layer ] (`com.ishik.jobportal.repository`)
  │ Extends JpaRepository to execute database queries.
  ├── JobRepository
  ├── EmployerRepository
  └── JobApplicationRepository
        │
        ▼
[ Entity / Database Layer ] (`com.ishik.jobportal.entity`)
  │ JPA Entities mapped to MySQL database tables.
  ├── Job (`jobs` table)
  ├── Employer (`employers` table)
  └── JobApplication (`job_applications` table)
        │
        ▼
[ MySQL Database ] (`myjobportal` database)
```

---

## 🗄️ Database Design & Schema

### 1. `jobs` Table
| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique Job ID |
| `title` | VARCHAR(255) | NOT NULL | Job Title |
| `description` | VARCHAR(2000) | NOT NULL | Detailed Description |
| `company` | VARCHAR(255) | NOT NULL | Company Name |
| `location` | VARCHAR(255) | NOT NULL | Job Location |
| `salary` | DOUBLE | NOT NULL | Annual Salary |
| `job_type` | VARCHAR(255) | NOT NULL | FULL_TIME / PART_TIME / INTERNSHIP / CONTRACT |
| `experience_required` | INT | NOT NULL | Minimum Years of Experience |
| `skills_required` | VARCHAR(500) | NULLABLE | Comma-separated required skills |
| `posted_date` | DATE | NOT NULL | Date posted |
| `is_active` | BIT(1) | NOT NULL | Active status |
| `employer_id` | BIGINT | FOREIGN KEY (`employers.id`) | Foreign key linking to employer |

### 2. `employers` Table
| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique Employer ID |
| `company_name` | VARCHAR(255) | NOT NULL | Company Name |
| `contact_person` | VARCHAR(255) | NOT NULL | Contact Person Name |
| `email` | VARCHAR(255) | NOT NULL, UNIQUE | Contact Email |
| `phone` | VARCHAR(15) | NULLABLE | Phone Number |
| `about` | VARCHAR(500) | NULLABLE | Company Profile |

### 3. `job_applications` Table
| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique Application ID |
| `applicant_name` | VARCHAR(255) | NOT NULL | Applicant Full Name |
| `email` | VARCHAR(255) | NOT NULL | Applicant Email |
| `phone` | VARCHAR(15) | NULLABLE | Contact Phone |
| `cover_letter` | VARCHAR(2000) | NULLABLE | Cover Letter Text |
| `resume_summary` | VARCHAR(1000) | NULLABLE | Summary of Experience |
| `applied_date` | DATE | NOT NULL | Application Date |
| `status` | VARCHAR(255) | NOT NULL | PENDING / REVIEWED / ACCEPTED / REJECTED |
| `job_id` | BIGINT | FOREIGN KEY (`jobs.id`) | Foreign key linking to job |

---

## 📡 REST API Endpoints

### 💼 Jobs API (`/api/jobs`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/jobs` | Retrieve all jobs |
| `GET` | `/api/jobs/{id}` | Retrieve single job by ID |
| `POST` | `/api/jobs` | Create a new job listing |
| `PUT` | `/api/jobs/{id}` | Update existing job listing |
| `DELETE` | `/api/jobs/{id}` | Delete job listing by ID |
| `GET` | `/api/jobs/search?keyword=java` | Search jobs by keyword |
| `GET` | `/api/jobs/type/{jobType}` | Filter jobs by type |
| `GET` | `/api/jobs/location?location=Bangalore` | Filter jobs by location |
| `GET` | `/api/jobs/count` | Get total job count |

### 🏢 Employers API (`/api/employers`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/employers` | Retrieve all employers |
| `GET` | `/api/employers/{id}` | Retrieve single employer by ID |
| `POST` | `/api/employers` | Register new employer |
| `PUT` | `/api/employers/{id}` | Update employer details |
| `DELETE` | `/api/employers/{id}` | Delete employer profile |

### 📄 Job Applications API (`/api/applications`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/applications` | Retrieve all applications |
| `GET` | `/api/applications/{id}` | Retrieve application by ID |
| `GET` | `/api/applications/job/{jobId}` | Get applications for a specific job |
| `POST` | `/api/applications/job/{jobId}` | Submit application for a job |
| `PUT` | `/api/applications/{id}/status?status=REVIEWED` | Update application status |
| `DELETE` | `/api/applications/{id}` | Delete an application |

---

## ⚙️ Configuration & Setup

### 1. MySQL Configuration
Make sure MySQL service is running locally on port 3306.

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/myjobportal?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 2. How to Run the Application
Open a terminal in the `My Job` directory and run:

```bash
# Using Maven Wrapper (Windows)
.\mvnw.cmd spring-boot:run

# Or using installed Maven
mvn spring-boot:run
```

Once started, open your browser and navigate to:
👉 `http://localhost:8080/`

---

## 🧪 Testing APIs with Postman

### Example Request: Create a Job Posting
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/jobs`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "title": "Spring Boot Developer",
  "description": "Develop high-performance REST APIs using Spring Boot and JPA.",
  "company": "TechCorp Solutions",
  "location": "Bangalore",
  "salary": 950000.0,
  "jobType": "FULL_TIME",
  "experienceRequired": 2,
  "skillsRequired": "Java, Spring Boot, Hibernate, MySQL, REST API"
}
```

### Example Request: Search Jobs by Keyword
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/jobs/search?keyword=Java`

### Example Request: Submit Job Application
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/applications/job/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "applicantName": "Ishika Sharma",
  "email": "ishika@example.com",
  "phone": "9876543210",
  "resumeSummary": "Java developer with experience in Spring Boot and MySQL.",
  "coverLetter": "I am passionate about backend engineering and eager to contribute to TechCorp Solutions."
}
```

---

## 🚀 Future Improvements
- Add Spring Security for Authentication (JWT / Role-based access for Employers and Job Seekers)
- Resume file upload (PDF/Docx storage using AWS S3 or local directory)
- Email notifications on application submission using Spring Email Starter
