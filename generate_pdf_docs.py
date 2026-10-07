import sys
import os
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, HRFlowable, PageBreak

def build_pdf(filename="Job_Portal_Project_Documentation.pdf"):
    doc = SimpleDocTemplate(
        filename,
        pagesize=letter,
        rightMargin=36,
        leftMargin=36,
        topMargin=36,
        bottomMargin=36
    )

    styles = getSampleStyleSheet()
    
    # Custom styles
    title_style = ParagraphStyle(
        'DocTitle',
        parent=styles['Heading1'],
        fontName='Helvetica-Bold',
        fontSize=24,
        leading=28,
        textColor=colors.HexColor("#1e3a8a"),
        spaceAfter=10
    )

    subtitle_style = ParagraphStyle(
        'DocSubtitle',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=12,
        leading=16,
        textColor=colors.HexColor("#475569"),
        spaceAfter=20
    )

    h1_style = ParagraphStyle(
        'SectionH1',
        parent=styles['Heading1'],
        fontName='Helvetica-Bold',
        fontSize=16,
        leading=20,
        textColor=colors.HexColor("#1d4ed8"),
        spaceBefore=14,
        spaceAfter=8
    )

    h2_style = ParagraphStyle(
        'SectionH2',
        parent=styles['Heading2'],
        fontName='Helvetica-Bold',
        fontSize=12,
        leading=16,
        textColor=colors.HexColor("#0f172a"),
        spaceBefore=10,
        spaceAfter=6
    )

    body_style = ParagraphStyle(
        'BodyTextCustom',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=13.5,
        textColor=colors.HexColor("#334155"),
        spaceAfter=6
    )

    code_style = ParagraphStyle(
        'CodeStyle',
        parent=styles['Normal'],
        fontName='Courier',
        fontSize=8.5,
        leading=11,
        textColor=colors.HexColor("#0f172a"),
        backColor=colors.HexColor("#f1f5f9"),
        spaceBefore=4,
        spaceAfter=6
    )

    story = []

    # Title Banner
    story.append(Paragraph("MyJobPortal - Complete Project Documentation", title_style))
    story.append(Paragraph("A Spring Boot 3 & MySQL Enterprise Job Portal Application | Comprehensive Technical Guide & Interview Handbook", subtitle_style))
    story.append(HRFlowable(width="100%", thickness=2, color=colors.HexColor("#2563eb"), spaceAfter=15))

    # Section 1: Executive Summary
    story.append(Paragraph("1. Executive Summary & Overview", h1_style))
    story.append(Paragraph(
        "<b>MyJobPortal</b> is a robust, production-ready full-stack web application designed to connect Job Seekers with Employers. "
        "Built using Java 17+, Spring Boot 3.2.5, Spring Data JPA, Hibernate, MySQL, and Thymeleaf, the application implements a clean "
        "<b>Client -> Controller -> Service -> Repository -> MySQL Database</b> architecture. It provides full CRUD capabilities for job listings, "
        "real-time keyword search across technical skill sets, employer profile management, and candidate job application tracking.",
        body_style
    ))

    # Section 2: Tech Stack
    story.append(Paragraph("2. Technical Stack & Key Frameworks", h1_style))
    tech_data = [
        [Paragraph("<b>Component</b>", body_style), Paragraph("<b>Technology Used</b>", body_style), Paragraph("<b>Role / Purpose</b>", body_style)],
        [Paragraph("Language", body_style), Paragraph("Java 17 / 25", body_style), Paragraph("Core Object-Oriented programming language", body_style)],
        [Paragraph("Framework", body_style), Paragraph("Spring Boot 3.2.5", body_style), Paragraph("Rapid application development & embedded Tomcat server", body_style)],
        [Paragraph("Web & REST API", body_style), Paragraph("Spring WebMVC", body_style), Paragraph("HTTP Request mapping (@RestController, @Controller)", body_style)],
        [Paragraph("ORM & Data Access", body_style), Paragraph("Spring Data JPA / Hibernate", body_style), Paragraph("Object-Relational Mapping & automated SQL queries", body_style)],
        [Paragraph("Database", body_style), Paragraph("MySQL Database", body_style), Paragraph("Relational data storage for jobs, employers & applications", body_style)],
        [Paragraph("Template Engine", body_style), Paragraph("Thymeleaf", body_style), Paragraph("Server-side HTML page rendering", body_style)],
        [Paragraph("Dependency Injection", body_style), Paragraph("Constructor Injection", body_style), Paragraph("Immutability & testable dependency management", body_style)]
    ]
    t_tech = Table(tech_data, colWidths=[110, 140, 280])
    t_tech.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), colors.HexColor("#e2e8f0")),
        ('GRID', (0,0), (-1,-1), 0.5, colors.HexColor("#cbd5e1")),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(t_tech)
    story.append(Spacer(1, 10))

    # Section 3: Architecture
    story.append(Paragraph("3. Application Architecture & Data Flow", h1_style))
    story.append(Paragraph(
        "The application enforces strict separation of responsibilities across 5 layers:<br/>"
        "• <b>Controller Layer:</b> Intercepts HTTP requests and returns JSON data (@RestController) or Thymeleaf views (@Controller).<br/>"
        "• <b>Service Layer:</b> Contains core business logic, input validation, and date assignment (@Service).<br/>"
        "• <b>Repository Layer:</b> Extends JpaRepository to handle MySQL queries without boilerplate SQL (@Repository).<br/>"
        "• <b>Entity Layer:</b> Maps Java classes directly to MySQL tables using JPA annotations (@Entity, @Table, @Id, @ManyToOne).<br/>"
        "• <b>MySQL Database:</b> Relational database storing persistent state.",
        body_style
    ))

    # Section 4: REST API Endpoints
    story.append(Paragraph("4. Complete REST API Specification", h1_style))
    api_data = [
        [Paragraph("<b>HTTP Method</b>", body_style), Paragraph("<b>Endpoint URI</b>", body_style), Paragraph("<b>Functionality</b>", body_style)],
        [Paragraph("GET", body_style), Paragraph("/api/jobs", body_style), Paragraph("Fetch all job listings", body_style)],
        [Paragraph("GET", body_style), Paragraph("/api/jobs/{id}", body_style), Paragraph("Fetch specific job by ID", body_style)],
        [Paragraph("POST", body_style), Paragraph("/api/jobs", body_style), Paragraph("Create a new job posting", body_style)],
        [Paragraph("PUT", body_style), Paragraph("/api/jobs/{id}", body_style), Paragraph("Update an existing job posting", body_style)],
        [Paragraph("DELETE", body_style), Paragraph("/api/jobs/{id}", body_style), Paragraph("Delete a job posting by ID", body_style)],
        [Paragraph("GET", body_style), Paragraph("/api/jobs/search?keyword=...", body_style), Paragraph("Real-time search across title, description & skills", body_style)],
        [Paragraph("GET", body_style), Paragraph("/api/employers", body_style), Paragraph("List all registered employers", body_style)],
        [Paragraph("POST", body_style), Paragraph("/api/applications/job/{jobId}", body_style), Paragraph("Submit application for a job", body_style)],
        [Paragraph("PUT", body_style), Paragraph("/api/applications/{id}/status", body_style), Paragraph("Update application status (PENDING/REVIEWED/ACCEPTED)", body_style)]
    ]
    t_api = Table(api_data, colWidths=[80, 180, 270])
    t_api.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), colors.HexColor("#dbeafe")),
        ('GRID', (0,0), (-1,-1), 0.5, colors.HexColor("#93c5fd")),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(t_api)
    story.append(Spacer(1, 10))

    # Section 5: Database Entities
    story.append(Paragraph("5. Database Relational Schema", h1_style))
    story.append(Paragraph(
        "The relational database schema comprises 3 interconnected tables in MySQL (<b>myjobportal</b> database):<br/>"
        "1. <b>jobs:</b> Primary key (id), title, description, company, location, salary, job_type, experience_required, skills_required, posted_date, is_active, employer_id (Foreign Key).<br/>"
        "2. <b>employers:</b> Primary key (id), company_name, contact_person, email (Unique), phone, about.<br/>"
        "3. <b>job_applications:</b> Primary key (id), applicant_name, email, phone, cover_letter, resume_summary, applied_date, status, job_id (Foreign Key).",
        body_style
    ))

    # Section 6: Interview Questions & Answers
    story.append(Paragraph("6. Key Technical Interview Questions & Answers", h1_style))

    q1 = "<b>Q1: What is the difference between @Controller and @RestController in Spring Boot?</b><br/>" \
         "<i>Answer:</i> @Controller is used for traditional MVC applications that return HTML views (e.g., Thymeleaf templates). " \
         "@RestController combines @Controller and @ResponseBody, automatically serializing return values directly into JSON format for API consumers."
    story.append(Paragraph(q1, body_style))
    story.append(Spacer(1, 4))

    q2 = "<b>Q2: Why did you use Constructor Injection instead of @Autowired on fields?</b><br/>" \
         "<i>Answer:</i> Constructor Injection is recommended because it makes dependencies immutable (marked as final), prevents NullPointerExceptions at runtime, and allows easy unit testing without launching a Spring context."
    story.append(Paragraph(q2, body_style))
    story.append(Spacer(1, 4))

    q3 = "<b>Q3: How does Spring Data JPA eliminate boilerplate database code?</b><br/>" \
         "<i>Answer:</i> By extending JpaRepository<Job, Long>, Spring Data JPA automatically provides full CRUD methods (findAll, findById, save, deleteById) and parses custom method names (e.g., findByJobType) into executable SQL at runtime."
    story.append(Paragraph(q3, body_style))
    story.append(Spacer(1, 4))

    q4 = "<b>Q4: Explain the database relationship between Job and Employer.</b><br/>" \
         "<i>Answer:</i> It is a Many-to-One relationship (@ManyToOne) from Job to Employer, and One-to-Many (@OneToMany) from Employer to Job. One employer can post multiple job listings, but each job belongs to exactly one employer."
    story.append(Paragraph(q4, body_style))

    story.append(Spacer(1, 15))
    story.append(HRFlowable(width="100%", thickness=1, color=colors.HexColor("#cbd5e1"), spaceAfter=10))
    story.append(Paragraph("<i>Documentation generated for MyJobPortal Project | Developed by Ishika Rathod</i>", ParagraphStyle('FooterStyle', parent=body_style, fontSize=8, textColor=colors.HexColor("#64748b"), alignment=1)))

    doc.build(story)
    print(f"Successfully generated {filename}")

if __name__ == "__main__":
    build_pdf()
